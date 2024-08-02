package kr.co.caloriebus.inquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.co.caloriebus.inquery.service.InqueryService;

@Controller
public class InqueryController {
	@Autowired
	private InqueryService inqueryService;
}
