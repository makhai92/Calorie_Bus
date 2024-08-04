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
public String cscMain() {
	return "faq/cscMain";
}
@GetMapping(value="/faqMain")
public String faqMain() {
	return "faq/faqMain";
}@GetMapping(value="/inqueryMain")
public String inqueryMain() {
	return "faq/inqueryMain";
}
}
