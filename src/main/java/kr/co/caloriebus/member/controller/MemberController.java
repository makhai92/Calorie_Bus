package kr.co.caloriebus.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.member.model.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/join")
	public String join() {
		return "member/join";
	}
	
	@GetMapping(value="/joinForm")
	public String joinForm() {
		return "member/joinForm";
	}
	
	@GetMapping(value="/login")
	public String login() {
		return "member/loginForm";
	}
}
