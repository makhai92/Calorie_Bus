package kr.co.caloriebus.faq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.faq.model.service.FaqService;

@Controller
@RequestMapping(value="/faq")
public class FaqController {
	@Autowired
	private FaqService faqService;

@GetMapping(value="/cscMain")
public String login() {
	return "faq/cscMain";
}
}
