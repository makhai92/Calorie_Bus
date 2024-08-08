package kr.co.caloriebus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.product.model.service.ProductService;

@Controller
public class HomeController {
	@Autowired
    private ProductService productService;
	
	@GetMapping(value="/")
	public String main(Model model) {
		List list = productService.selectAllProduct();
    	model.addAttribute("list",list);
        
		return "index";
	}
	@GetMapping(value="//")
	public String login(HttpSession session) {
		Member member = new Member(2, "admin", "1234", "관리자", "01000000000", "관리자 집", "admin@email", "2024-08-04", 1, 0, null, null,null);
		session.setAttribute("member", member);
		return "redirect:/";
	}
}
