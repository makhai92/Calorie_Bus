package kr.co.caloriebus.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.admin.model.service.AdminService;
import kr.co.caloriebus.faq.model.service.FaqService;
import kr.co.caloriebus.product.model.dto.Funding;

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
    public String getPurchaseHistory(Model model) {
        List<Funding> purchaseList = adminService.getAllFunding(); // 구매 리스트를 가져오는 서비스 메서드 호출
        model.addAttribute("list", purchaseList); // 모델에 'list'라는 이름으로 데이터 추가
        System.out.println(purchaseList);
        return "admin/purchaseHistory";
    }
	@GetMapping(value="/changeOrderState")
	public String changeOrderState(Funding f, Model model) {
		int result = adminService.changeOrderState(f);
		if(result>0) {
			return "redirect:/admin/purchaseHistory";
		}else {
			model.addAttribute("title", "변경 실패");
			model.addAttribute("msg", "창모형에게 문의하세요");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/admin/purchaseHistory");
			return "common/"; 
		}
		
	}

}