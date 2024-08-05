package kr.co.caloriebus.newsletter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.newsletter.model.service.NewsLetterService;

@Controller
@RequestMapping(value="/newsletter")
public class NewsLetterController {
	@Autowired
	private NewsLetterService newsLetterService;
	@Value("${file.root}")
	private String root;
}
