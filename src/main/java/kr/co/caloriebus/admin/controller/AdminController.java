package kr.co.caloriebus.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.admin.model.dto.MemberListData;
import kr.co.caloriebus.admin.model.dto.PrizeListData;
import kr.co.caloriebus.admin.model.dto.PurchaseListData;
import kr.co.caloriebus.admin.model.service.AdminService;
import kr.co.caloriebus.faq.model.service.FaqService;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.rulletpage.model.dto.RulletPage;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@GetMapping(value="/adminMain")
public String adminMain() {
	return "admin/adminMain";
}
	@GetMapping("/purchaseHistory")
    public String getPurchaseHistory(int reqPage,Model model) {
        PurchaseListData pld = adminService.getAllFunding(reqPage); // 구매 리스트를 가져오는 서비스 메서드 호출
        model.addAttribute("list", pld.getList()); // 모델에 'list'라는 이름으로 데이터 추가
        model.addAttribute("pageNavi",pld.getPageNavi());
        return "admin/purchaseHistory";
    }
	
	
	@GetMapping("/gradeChange")
	public String getGradeChange(int reqPage,Model model) {
		MemberListData mld = adminService.getAllMember(reqPage);
		model.addAttribute("list" , mld.getList());
		model.addAttribute("pageNavi" , mld.getPageNavi());
		return "admin/gradeChange";
	}
	
	@GetMapping("/prizeDetails")
	public String getPrizeDetail(int reqPage,Model model) {
		PrizeListData pld = adminService.getAllDetail(reqPage);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi",pld.getPageNavi());
		return "admin/prizeDetails";	
	}
	
	
	@GetMapping(value="/changeOrderState")
	public String changeOrderState(Funding f, Model model) {
		int result = adminService.changeOrderState(f);
		if(result>0) {
			return "redirect:/admin/purchaseHistory?reqPage=1";
		}else {
			model.addAttribute("title", "변경 실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/purchaseHistory");
			return "common/"; 
		}
	    }
	
	
	@GetMapping("/memberLevelChange")	
	public String memberLevelChange(Member m,Model model) {
		int result = adminService.memberLevelChange(m);
		if(result>0) {
			return "redirect:/admin/gradeChange?reqPage=1";
		}else {
			model.addAttribute("title", "변경 실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/gradeChange");
				return "common/msg";
		}
	}
	
	@GetMapping("/eventStateUpdate")
	public String eventStateUpdate(RulletPage r, Model model) {
		int result = adminService.eventStateUpdate(r);
		if(result>0) {
			model.addAttribute("title", "지급 완료");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "success");
			return "redirect:/admin/prizeDetails?reqPage=1";
		}else {
			model.addAttribute("title", "지급 실패");
			model.addAttribute("msg", "개발자에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/prizeDetails");
				return "common/msg";
		}
	}
}