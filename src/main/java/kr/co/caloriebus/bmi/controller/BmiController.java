package kr.co.caloriebus.bmi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/bmi")
public class BmiController {
	
	@GetMapping(value = "/BMI")
	public String MBI() {
		return"bmi/BMI";
	}
}
