package kr.co.caloriebus.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.caloriebus.member.model.dto.Member;
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
	
	@GetMapping(value="/loginForm")
	public String login() {
		return "member/loginForm";
	}
	
	// 아이디 중복 체크 -> member 있으면 1, 없으면 0 리턴
	@ResponseBody
	@GetMapping(value="/checkId")
	public int checkId(String checkId) {
		Member member = memberService.selectOneMember(checkId);
		int result = 0;
		if (member != null) {
			result = 1;
		}
		return result;
	}
	
	// 회원 등록
	@PostMapping(value="/insertMember")
	public String insertMember(Member m, Model model) {
		int result = memberService.insertMember(m);
		if (result > 0) {
			return "redirect:/";			
		}
		else {
			return "member/join";
		}
	}
	
	// 로그인
	@GetMapping(value="/login")
	public String loginMember(Member m, HttpSession session) {
		Member member = memberService.selectOneMember(m);
		if (member != null) {
			session.setAttribute("member", member);
			return "redirect:/";			
		}
		else {
			return "redirect:member/loginForm";
		}
	}
	
	// 로그아웃
	@GetMapping(value="/logout")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";			
	}
	// 마이페이지로 이동
	@GetMapping(value="/mypage")
	public String mypage() {
		return "member/mypage";
	}
}
