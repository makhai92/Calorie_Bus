package kr.or.caloriebus.faq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.caloriebus.faq.model.service.FaqService;

@Controller
@RequestMapping(value="/faq")
public class FaqController {
	@Autowired
	private FaqService faqService;
}
