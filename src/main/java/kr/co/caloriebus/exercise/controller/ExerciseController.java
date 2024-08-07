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
	public String list(int reqPage,Model model) {
		ExerciseListData eld = exerciseService.selectBoardList(reqPage);
		model.addAttribute("list",eld.getList());
		model.addAttribute("pageNavi",eld.getPageNavi());
		return "exercise/list";
	}
	
	@GetMapping(value="/editFrm")
	public String editFrm() {
		return "exercise/editFrm";
	}
	
	@PostMapping(value="/write")
	public String write(Exercise e, MultipartFile[] upfile, Model model) {
		ArrayList<ExerciseFile> fileList = new ArrayList<ExerciseFile>();
		if(!upfile[0].isEmpty()) {
			String savepath = root+"/exericse/";
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
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
			model.addAttribute("loc","/exercise/list?reqPage=1");
			return "common/msg"; 
		}
		return "redirect:/exercise/editFrm";
	}
	
	@GetMapping(value = "viewFrm")
	public String viewFrm(int boardNo, Model model) {
		Exercise e = exerciseService.selectOneBoard(boardNo);
		if(e == null) {
			model.addAttribute("title","조회실패");
			model.addAttribute("msg","해당 게시글이 존재하지 않습니다.");
			model.addAttribute("icon","info");
			model.addAttribute("loc","/board/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("e",e);
			return "exercise/viewFrm";
		}
	}
	/*
	@GetMapping(value = "/filedown")
	public void filedown(ExerciseFile exerciseFile, HttpServletResponse response) {
		String savepath = root+"/exercise/";
		fileUtils.downloadFile(savepath,exerciseFile.getFilename(),exerciseFile.getFilepath(),response);
	}
	*/
	@GetMapping(value = "/delete")
	public String delete(int boardNo, Model model) {
		List<ExerciseFile> list = exerciseService.deleteBoard(boardNo);
		if(list == null) {
			model.addAttribute("title", "삭제실패");
			model.addAttribute("msg", "존재하지 않는 게시글입니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/exercise/list?reqPage=1");
		}else {
			String savepath = root+"/exercise";
			for(ExerciseFile file: list) {
				File delFile = new File(savepath+file.getFilepath());
				delFile.delete();
			}
			model.addAttribute("title", "삭제성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/exercise/list?reqPage=1");
		}
		return "common/msg";
	}
	
	@GetMapping(value = "/updateFrm")
	public String updateFrm(int boardNo,Model model) {
		Exercise e = exerciseService.getOneBoard(boardNo);
		model.addAttribute("e", e);
		return "exercise/updateFrm";
	}
	
	@PostMapping(value = "/update")
	public String update(Exercise e, MultipartFile[] upfile,int[] delFileNo, Model model) {
		List<ExerciseFile> fileList = new ArrayList<ExerciseFile>();
		String savepath = root+"/exercise/";
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				ExerciseFile exerciseFile = new ExerciseFile();
				exerciseFile.setFilename(filename);
				exerciseFile.setFilepath(filepath);
				exerciseFile.setFileNo(e.getBoardNo());
			}
		}
		List<ExerciseFile> delFileList = exerciseService.updateBoard(e,fileList,delFileNo);
		if(delFileList == null) {
			model.addAttribute("title", "수정실패");
			model.addAttribute("msg", "처리중");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/exercise/list?=reqPage=1");
			return "common/msg";
		}else {
			for(ExerciseFile exerciseFile : delFileList) {
				File delFile = new File(savepath+exerciseFile.getFilepath());
				delFile.delete();
			}
			return "redirect:/exercise/viewFrm?boardNo="+e.getBoardNo();
		}
	}
	
}
