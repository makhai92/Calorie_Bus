package kr.co.caloriebus.inquery.controller;



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
import kr.co.caloriebus.util.FileUtils;
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
			model.addAttribute("loc", "/member/myinquery?reqPage=1");
			return "common/msg";
		}
		return "redirect:/inquery/inqueryEditor";
		
	}
	@GetMapping(value = "/inqueryView")
	public String view(int inqueryNo,String check, Model model, @SessionAttribute(required = false) Member member) {
		int memberNo = 0;
		if (member != null) {
			memberNo = member.getMemberNo();
		}
		
		Inquery i = inqueryService.selectOneInquery(inqueryNo, check ,memberNo);
		if (i == null) {
			model.addAttribute("title", "조회 실패");
			model.addAttribute("msg", "해당 게시글이 존재하지 않습니다..");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/inquery/inqueryMain?reqPage=1");
			return "common/msg";
		} else {
			model.addAttribute("i", i);
			return "inquery/inqueryView";
		}
	}
	@GetMapping(value = "/filedown")
	public void filedown(InqueryFile inqueryFile, HttpServletResponse response) {
		String savepath = root + "/inquery/";
		FileUtils.downloadFile(savepath, inqueryFile.getFileName(), inqueryFile.getFilePath(), response);
	}
	
	@PostMapping(value = "/insertReply")
	public String insertReply(InqueryReply ir,Model model) {
		int result = inqueryService.insertReply(ir);
		if (result > 0) {
			model.addAttribute("title", "답변 작성 성공");
			model.addAttribute("msg", "답변이 작성 되었습니다");
			model.addAttribute("icon", "success");
		} else {
			model.addAttribute("title", "답변 작성 실패");
			model.addAttribute("msg", "답변 작성 중 문제가 발생했습니다.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/inquery/inqueryView?inqueryNo=" + ir.getInqueryRef());
		return "common/msg";
	}
	@GetMapping(value = "/deleteReply")
	public String deleteReply(InqueryReply ir, Model model) {
		int result = inqueryService.deleteReply(ir);
		if (result > 0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "댓글이 삭제되었습니다!");
			model.addAttribute("icon", "success");
		} else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("title", "잠시 후 다시 시도해주세요..");
			model.addAttribute("title", "warning");
		}
		model.addAttribute("loc", "/inquery/inqueryView?inqueryNo=" + ir.getInqueryRef());
		return "common/msg";
	}
	@GetMapping(value = "/inqueryDelete")
	public String delete(int inqueryNo, Model model) {
		List<InqueryFile> list = inqueryService.deleteInquery(inqueryNo);
		if (list == null) {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "해당 게시글이 존재하지 않습니다..");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/inquery/inqueryMain?reqPage=1");
		} else {
			String savepath = root + "/notice/";
			for (InqueryFile file : list) {
				File delFile = new File(savepath + file.getFilePath());
				delFile.delete();
			}
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다..");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/inquery/inqueryMain?reqPage=1");
		}
		return "common/msg";
	}	
	@PostMapping(value = "/updateReply")
	public String updateReply(InqueryReply ir, Model model) {
		int result = inqueryService.updateReply(ir);
		System.out.println(result);
	if(result>0) {
		model.addAttribute("title", "성공");
		model.addAttribute("msg", "답변이 수정되었습니다!");
		model.addAttribute("icon", "success");
	} else {
		model.addAttribute("title", "실패");
		model.addAttribute("msg", "잠시 후 다시 시도해주세요..");
		model.addAttribute("icon", "warning");
	}
	model.addAttribute("loc", "/inquery/inqueryView?inqueryNo=" + ir.getInqueryRef());
	return "common/msg";

}
	@GetMapping(value = "inqueryUpdateFrm")
	public String updateFrm(int inqueryNo, Model model) {
		Inquery i = inqueryService.getOneInquery(inqueryNo);
		model.addAttribute("i", i);
		return "inquery/inqueryUpdateFrm";
	}
	@PostMapping(value="/inqueryUpdate")
	public String update(Inquery i, MultipartFile[] upfile, int[] delFileNo, Model model) {
	List<InqueryFile> fileList = new ArrayList<InqueryFile>();
	String savepath = root + "/inquery/";
	if(!upfile[0].isEmpty()) {
		for (MultipartFile file : upfile) {
			String fileName = file.getOriginalFilename();
			String filePath = FileUtils.upload(savepath, file);
			InqueryFile inqueryFile = new InqueryFile();
			inqueryFile.setFileName(fileName);
			inqueryFile.setFilePath(filePath);
			inqueryFile.setInqueryNo(i.getInqueryNo());
			fileList.add(inqueryFile);
		}
	}
	List<InqueryFile> delFileList = inqueryService.inqueryUpdate(i,fileList,delFileNo);
	if (delFileList == null) {
		model.addAttribute("title", "수정 실패");
		model.addAttribute("msg", "처리중 문제가 발생했습니다.. 잠시후 다시 시도해주세요..");
		model.addAttribute("icon", "error");
		model.addAttribute("loc", "/notice/list?reqPage=1");
		return "common/msg";
	} else {
		for (InqueryFile inqueryFile : delFileList) {
			File delFile = new File(savepath + inqueryFile.getFilePath());
			delFile.delete();
		}
		return "redirect:/inquery/inqueryView?inqueryNo="+i.getInqueryNo();
	}
	}
	



	
	
	
	
	
	
	
	
	
	
	
	
	
}
