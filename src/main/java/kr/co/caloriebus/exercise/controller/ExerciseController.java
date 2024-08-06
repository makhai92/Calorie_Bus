package kr.co.caloriebus.exercise.controller;

import java.util.ArrayList;

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

import kr.co.caloriebus.exercise.model.dto.Exercise;
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
	public String list() {
		return "exercise/list";
	}
	
	/*
	@GetMapping(value="/list")
	public String list(int reqPage,Model model) {
		ExerciseListData eld = exerciseService.selectBoardList(reqPage);
		model.addAttribute("list",eld.getList());
		model.addAttribute("pageNavi",eld.getPageNavi());
		return "exercise/list";
	}
	*/
	
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
	public String write(Exercise e,MultipartFile[] files, Model model) {
		ArrayList<ExerciseFile> fileList = new ArrayList<ExerciseFile>();
		if(!files[0].isEmpty()) {
			String savepath = root+"/exericse/";
			for(int i=0;i<files.length;i++) {
				String filename = files[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, files[i]);
				ExerciseFile exerciseFile = new ExerciseFile();
				exerciseFile.setFilepath(filepath);
				exerciseFile.setFilename(filename);
				fileList.add(exerciseFile);
			}
		}
		int result = exerciseService.insertBoard(e,fileList);
		if(result>0) {
			model.addAttribute("title","게시글 작성 성공");
			model.addAttribute("msg","게시글 작성 성공");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","게시글 작성 실패");
			model.addAttribute("msg","게시글 작성 실패");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/exercise/list?category="+e.getBoardCategory()+"&reqPage=1");
		return "common/msg"; 
	}
	
	/*
	@GetMapping(value = "/view")
	public String view(int boardNo,String check, Model model, @SessionAttribute(required=false) Member member) {
		int memberNo=0;
		if(member != null) {
			memberNo = member.getMemberNo();
		}
		//로그인이 되어있지 않으면 memberNo =0/로그인이 되어있으면 memberNo=로그인한 회원번호
		Exercise e = exerciseService.selectOneBoard(boardNo,check,memberNo);
		if(e == null) {
			model.addAttribute("title", "조회실패");
			model.addAttribute("msg", "해당 게시글이 존재하지 않습니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/notice/list?reqpage=1");
			return "common/msg";
		}else {
		model.addAttribute("e", e);
		return "exercise/view";	
		}
		
	}
	*/
	
	@GetMapping(value="/viewFrm")
	public String viewFrm() {
		return "exercise/viewFrm";
	}
	
}
