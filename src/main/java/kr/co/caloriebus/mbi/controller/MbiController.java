package kr.co.caloriebus.mbi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/mbi")
public class MbiController {
	
	@GetMapping(value = "/MBI")
	public String MBI() {
		return"mbi/MBI";
	}
}
