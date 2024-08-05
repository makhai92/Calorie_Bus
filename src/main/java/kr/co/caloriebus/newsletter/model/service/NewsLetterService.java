package kr.co.caloriebus.newsletter.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.newsletter.model.dao.NewsLetterDao;

@Service
public class NewsLetterService {
	@Autowired
	private NewsLetterDao newsletterDao; 
}
