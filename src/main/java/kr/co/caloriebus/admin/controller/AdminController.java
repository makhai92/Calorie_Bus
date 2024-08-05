package kr.co.caloriebus.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.admin.model.service.AdminService;
import kr.co.caloriebus.faq.model.service.FaqService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@GetMapping(value="/adminMain")
public String adminMain() {
	return "admin/adminMain";
}

}