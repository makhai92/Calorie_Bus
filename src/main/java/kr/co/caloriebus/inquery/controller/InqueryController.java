package kr.co.caloriebus.inquery.controller;



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
import kr.co.caloriebus.util.FileUtils;
import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.inquery.dto.Inquery;
import kr.co.caloriebus.inquery.dto.InqueryFile;
import kr.co.caloriebus.inquery.dto.InqueryListData;
import kr.co.caloriebus.inquery.dto.InqueryReply;
import kr.co.caloriebus.inquery.service.InqueryService;
import kr.co.caloriebus.member.model.dto.Member;

@Controller
@RequestMapping(value = "/inquery")
public class InqueryController {
	@Autowired
	private InqueryService inqueryService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils FileUtils;
	
	@GetMapping(value = "/inqueryMain")
	public String list(Model model,int reqPage) {
		InqueryListData ild = inqueryService.selectInqueryList(reqPage);
		model.addAttribute("list", ild.getList());
		model.addAttribute("pageNavi",ild.getPageNavi());
		return "inquery/inqueryMain";
	}
	@GetMapping(value="/inqueryEditor")
	public String inqueryEditor(@SessionAttribute(required = false) Member member) {
		return "inquery/inqueryEditor";
	}
	@ResponseBody
	@PostMapping(value = "/editorImage", produces ="plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root + "/inquery/inqueryEditor/";
		String filepath = FileUtils.upload(savepath, upfile);
		return "/inquery/inqueryEditor/" + filepath;
		}
	
	@PostMapping(value="/write")
	public String write(Inquery i, MultipartFile[] upfile, Model model) {
		List<InqueryFile> fileList = new ArrayList<InqueryFile>();
		
		if(!upfile[0].isEmpty()) {
			String savepath = root + "/inquery/";
			for (MultipartFile file : upfile) {
				String fileName = file.getOriginalFilename();
				String filePath = FileUtils.upload(savepath,file);
				InqueryFile inqueryFile = new InqueryFile();
				inqueryFile.setFileName(fileName);
				inqueryFile.setFilePath(filePath);
				fileList.add(inqueryFile);
			}
		}
		int result = inqueryService.insertInquery(i, fileList);
		if(result > 0) {
			model.addAttribute("title", "작성 완료");
			model.addAttribute("msg", "1:1 문의 작성을 완료하였습니다!");
			model.addAttribute("icon","success");
			model.addAttribute("loc", "/inquery/inqueryMain?reqPage=1");
			return "common/msg";
		}
		return "redirect:/inquery/inqueryEditor";
		
	}
	@GetMapping(value = "/inqueryView")
	public String view(int inqueryNo,String check, Model model, @SessionAttribute(required = false) Member member) {
		System.out.println(1);
		int memberNo = 0;
		if (member != null) {
			memberNo = member.getMemberNo();
			System.out.println(memberNo);
		}
		
		Inquery i = inqueryService.selectOneInquery(inqueryNo, check ,memberNo);
		System.out.println(i);
		if (i == null) {
			model.addAttribute("title", "조회 실패");
			model.addAttribute("msg", "해당 게시글이 존재하지 않습니다..");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/inquery/inqueryMain?reqPage=1");
			return "common/msg";
		} else {
			model.addAttribute("i", i);
			System.out.println("move to inquery view");
			return "inquery/inqueryView";
		}
	}
	@GetMapping(value = "/filedown")
	public void filedown(InqueryFile inqueryFile, HttpServletResponse response) {
		String savepath = root + "/inquery/";
		FileUtils.downloadFile(savepath, inqueryFile.getFileName(), inqueryFile.getFilePath(), response);
	}
	/*
	@PostMapping(value = "/insertReply")
	public String insertReply(InqueryReply ir,Model model) {
		int reuslt = inqueryService.insertReply(ir);
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 마이페이지 용 문의 내역 조회
	@GetMapping(value="/myinquery")
	public String myinquery(Model model, @SessionAttribute Member member, int reqPage) {
		int memberNo = member.getMemberNo();
		InqueryListData ild = inqueryService.selectMyInqueryList(memberNo, reqPage);
		model.addAttribute("list", ild.getList());
		model.addAttribute("pageNavi",ild.getPageNavi());
		model.addAttribute("category", "myinquery");
		return "member/myinquery";
	}
}
