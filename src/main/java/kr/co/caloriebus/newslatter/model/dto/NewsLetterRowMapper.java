package kr.co.caloriebus.newslatter.model.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.newsletter.model.service.NewsLetterService;

@Controller
@RequestMapping(value = "/newsletter")
public class NewsLetterRowMapper {
	@Autowired
	private NewsLetterService newsLetterService;
}
