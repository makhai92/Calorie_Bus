package kr.co.caloriebus.newsletter.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.board.model.service.BoardService;
import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFile;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterListData;
import kr.co.caloriebus.newsletter.model.service.NewsLetterService;
import kr.co.caloriebus.util.FileUtils;

@Controller
@RequestMapping(value="/newsletter")
public class NewsLetterController {
	
	@Autowired
	private NewsLetterService newsletterService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value = "/listForm")
	public String list(int reqPage,Model model) {
		NewsLetterListData bld = newsletterService.selectNewsLetterList(reqPage);
		model.addAttribute("list",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		return "newsletter/listForm";
	}
	@GetMapping(value="/writeForm")
	public String writeForm() {
		return "newsletter/writeForm";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/newsletter/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/newsletter/editor/"+filepath;
	}
	
	@PostMapping(value="/write")
	public String write(NewsLetter nl,MultipartFile[] files, Model model) {
		ArrayList<NewsLetterFile> fileList = new ArrayList<NewsLetterFile>();
		if(!files[0].isEmpty()) {
			String savepath = root+"/newsletter/";
			for(int i=0;i<files.length;i++) {
				String filename = files[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, files[i]);
				NewsLetterFile newsletterFile = new NewsLetterFile();
				newsletterFile.setFilepath(filepath);
				newsletterFile.setFilename(filename);
				fileList.add(newsletterFile);
			}
		}
		int result = newsletterService.insertNewsLetter(nl,fileList);
		if(result>0) {
			model.addAttribute("title","게시글 작성 성공");
			model.addAttribute("msg","게시글 작성 성공");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","게시글 작성 실패");
			model.addAttribute("msg","게시글 작성 실패");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/newsletter/list?reqPage=1");
		return "common/msg"; 
	}
}
