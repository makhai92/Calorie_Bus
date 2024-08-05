package kr.co.caloriebus.rulletpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RulletpageController {

	@GetMapping("/rulletpage/rullet")
	public String RulletPage() {
		return "rulletpage/rullet";
	}
}
