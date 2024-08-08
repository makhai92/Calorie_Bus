package kr.co.caloriebus.exercise.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.exercise.model.dto.Exercise;
import kr.co.caloriebus.exercise.model.dto.ExerciseComment;
import kr.co.caloriebus.exercise.model.dto.ExerciseFile;
import kr.co.caloriebus.exercise.model.dto.ExerciseListData;
import kr.co.caloriebus.exercise.model.service.ExerciseService;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.util.FileUtils;


@Controller
@RequestMapping(value = "/exercise")
public class ExerciseController {
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	
	@GetMapping(value="/list")
	public String list(int reqPage, Model model) {
		ExerciseListData eld = exerciseService.selectBoardList(reqPage);
		model.addAttribute("list",eld.getList());
		model.addAttribute("pageNavi",eld.getPageNavi());
		//System.out.println(eld);
		return "exercise/list";
	}
	
	@GetMapping(value="/editFrm")
	public String editFrm() {
		return "exercise/editFrm";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/exercise/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/exercise/editor/"+filepath;
	}

	@PostMapping(value="/write")
	public String write(Exercise e, MultipartFile[] upfile, Model model) {
		ArrayList<ExerciseFile> fileList = new ArrayList<ExerciseFile>();
		if(!upfile[0].isEmpty()) {
			//System.out.println(1);
			String savepath = root+"/exericse/";
			for(int i=0;i<upfile.length;i++) {
				String filename = upfile[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, upfile[i]);
				ExerciseFile exerciseFile = new ExerciseFile();
				exerciseFile.setFilepath(filepath);
				exerciseFile.setFilename(filename);
				fileList.add(exerciseFile);
			}
		}
		int result = exerciseService.insertExercise(e,fileList);
		if(result>0) {
			model.addAttribute("title","게시글 작성 성공");
			model.addAttribute("msg","게시글 작성 성공");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","게시글 작성 실패");
			model.addAttribute("msg","게시글 작성 실패");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/exercise/list?&reqPage=1");
		return "common/msg"; 
	}
	
	@GetMapping(value="/viewFrm")
	public String viewFrm(int boardNo,String check,@SessionAttribute (required=false)Member member,Model model) {
		//System.out.println(boardNo);
		Exercise e = null;
		if(member != null) {
			e = exerciseService.selectExercise(member.getMemberNo(),boardNo,check);	
			model.addAttribute("memberNo",member.getMemberNo());
		}else {
			e = exerciseService.selectExercise(-1,boardNo,check);
			model.addAttribute("memberNo",-1);
		}
		model.addAttribute("exercise",e);
		//System.out.println(e);
		return "exercise/viewFrm";
	}
	
	@GetMapping(value="/filedown")
	public void filedown(BoardFile bf,HttpServletResponse response) {
		String savepath = root+"/exercise/";
		fileUtils.downloadFile(savepath, bf.getFilename(), bf.getFilepath(), response);
	}
	
	@ResponseBody
	@PostMapping(value = "/exerciseLikePush")
	public int boardLikePush(int boardNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = exerciseService.exerciseLikePush(boardNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	
	@ResponseBody
	@PostMapping(value="/exerciseCommentLikePush")
	public int CommentLikePush(int boardCommentNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = exerciseService.exerciseCommentLikePush(boardCommentNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	
	@PostMapping(value="/insertExerciseComment")
	public String insertBoardComment(ExerciseComment ec,@SessionAttribute(required=false)Member member) {
		ec.setMemberNo(member.getMemberNo());
		int result = exerciseService.insertExerciseComment(ec);
		return "redirect:/board/view?boardNo="+ec.getBoardRef();
	}
	
	//삭제
	@GetMapping(value = "/delete")
	public String deleteExercise(int boardNo, Model model) {
		List<ExerciseFile> list = exerciseService.deleteExercise(boardNo);
		if(list == null) {
			model.addAttribute("title", "삭제실패");
			model.addAttribute("msg", "존재하지 않는 게시글입니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/exercise/list?reqPage=1");
		}else {
			String savepath = root+"/exercise";
			for(ExerciseFile file : list) {
				File delfile = new File(savepath+file.getFilepath());
				delfile.delete();
			}
			model.addAttribute("title", "삭제성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/exercise/list?reqPage=1");
		}
		return "common/msg";
	}
	
	@GetMapping(value = "/updateFrm")
	public String updateFrm(int boardNo, Model model) {
		return "exercise/updateFrm";
	}
	
	
	
}
