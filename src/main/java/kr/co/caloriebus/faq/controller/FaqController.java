package kr.co.caloriebus.faq.controller;

import java.io.Console;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.faq.model.dto.Faq;
import kr.co.caloriebus.faq.model.dto.FaqListData;
import kr.co.caloriebus.faq.model.dto.FaqSearch;
import kr.co.caloriebus.faq.model.service.FaqService;


@Controller
@RequestMapping(value="/faq")
public class FaqController {
	@Autowired
	private FaqService faqService;

@GetMapping(value="/cscMain")
public String cscMain() {
	return "faq/cscMain";
}
@GetMapping(value="/faqMain")
public String faqMain(int reqPage,Model model) {
	FaqListData fld = faqService.selectAllFaq(reqPage);
	model.addAttribute("list", fld.getList());
	model.addAttribute("pageNavi", fld.getPageNavi());
	return "faq/faqMain";
}

@GetMapping(value="/faqWriter")
public String faqWriter() {
	return "faq/faqWriter";
}
@PostMapping(value = "/write")
public String write(Faq f, Model model) {
	int result = faqService.insertFaq(f);
	System.out.println(f);
	if (result > 0) {
		model.addAttribute("title", "작성 완료");
		model.addAttribute("msg", "공지사항 작성에 성공하였습니다!");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/faq/faqMain?reqPage=1");
		return "common/msg";
	}
	return "redirect:/faq/faqView";
}
@GetMapping(value = "/faqView")
public String view(int faqNo,Model model) {
	Faq f = faqService.selectOneFaq(faqNo);
	
	if (f == null) {
		model.addAttribute("title", "조회 실패");
		model.addAttribute("msg", "해당 게시글이 존재하지 않습니다..");
		model.addAttribute("icon", "info");
		model.addAttribute("loc", "/faq/faqMain?reqPage=1");
		return "common/msg";
	} else {
		model.addAttribute("f", f);
		return "faq/faqView";
	}
}
@GetMapping(value = "/faqDelete")
public String delete(int faqNo, Model model) {
	int result= faqService.deleteFaq(faqNo);
	if(result == 0) {
		model.addAttribute("title", "삭제 실패");
		model.addAttribute("msg", "해당 게시글이 존재하지 않습니다..");
		model.addAttribute("icon", "error");
		model.addAttribute("loc", "/faq/faqMain?reqPage=1");
	}else {
		model.addAttribute("title", "삭제 성공");
		model.addAttribute("msg", "게시글이 삭제되었습니다..");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/faq/faqMain?reqPage=1");
	}
	return "common/msg";
}
@GetMapping(value = "/faqUpdate")
public String updateFrm(int faqNo , Model model) {
	Faq f = faqService.getOneFaq(faqNo);
	model.addAttribute("f", f);
	return "faq/faqUpdate";
}
@PostMapping("/update")
public String update(Faq f, Model model) {
    int result = faqService.updateFaq(f);
    System.out.println(f);
    if (result > 0) {
        model.addAttribute("title", "성공");
        model.addAttribute("msg", "FAQ가 수정되었습니다!");
        model.addAttribute("icon", "success");
        model.addAttribute("loc", "/faq/faqView?faqNo="+f.getFaqNo());
        return "common/msg";
    } else {
        model.addAttribute("title", "실패");
        model.addAttribute("msg", "수정 등록에 실패하였습니다.");
        model.addAttribute("icon", "warning");
        model.addAttribute("loc", "/faq/faqMain?reqPage=1");
        return "common/msg";
    }
}	

@GetMapping(value="/faqSearch")
public String faqSearch(FaqSearch fs,int reqPage,Model model) {
	FaqListData fld = faqService.searchAllFaq(fs,reqPage);
	model.addAttribute("list", fld.getList());
	model.addAttribute("pageNavi", fld.getPageNavi());
	model.addAttribute("searchField",fs.getSearchField());
	model.addAttribute("keyword",fs.getKeyword());
	return "faq/faqMain";
}

	
}

	

