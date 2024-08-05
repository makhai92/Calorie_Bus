package kr.co.caloriebus.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.member.model.service.MemberService;
import kr.co.caloriebus.util.Message;

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
	
	// alert 띄우는 용도
	@GetMapping(value="alertMsg")
	private String alertMsg(Message data, Model model) {
        model.addAttribute("data", data);
        return "etc/alertMsg";
    }
	
	// 아이디 중복 체크 -> member 있으면 1, 없으면 0 리턴
	@ResponseBody
	@GetMapping(value="/checkId")
	public int checkId(String checkId) {
		int result = 0;
		Member member = memberService.selectOneMember(checkId);
		if (member != null) {
			result = 1;
		}
		return result;
	}
	
	// 이메일 인증
	@ResponseBody
	@PostMapping(value="/verifyEmail")
	public int verifyEmail(String memberEmail) {
		int veriCode = memberService.verifyEmail(memberEmail);
		return veriCode;
	}
	
	// 회원 등록
	@PostMapping(value="/insertMember")
	public String insertMember(Member m, Model model) {
		int result = memberService.insertMember(m);
		if (result > 0) {
			Message data = new Message("회원가입이 완료되었습니다.", "/member/loginForm");
			return alertMsg(data, model);
		}
		else {
			Message data = new Message("회원가입에 실패했습니다.", "/member/join");
			return alertMsg(data, model);
		}
	}
	
	// 로그인
	@PostMapping(value="/login")
	public String loginMember(Member m, HttpSession session, Model model) {
		Member member = memberService.selectOneMember(m);
		if (member != null) {
			session.setAttribute("member", member);
			return "redirect:/";
		}
		else {
			Message data = new Message("아이디나 비밀번호를 확인해 주세요.", "/member/loginForm");
			return alertMsg(data, model);
		}
	}
	
	// 로그아웃
	@GetMapping(value="/logout")
	public String logoutMember(HttpSession session, Model model) {
		session.invalidate();
		Message data = new Message("로그아웃되었습니다.", "/");
		return alertMsg(data, model);			
	}
	
	// 아이디 찾기 페이지로 이동
	@GetMapping(value="/forgotId")
	public String forgotId() {
		return "member/forgotId";
	}
	
	// 비밀번호 찾기 페이지로 이동
	@GetMapping(value="/forgotPw")
	public String forgotPw() {
		return "member/forgotPw";
	}

	// 마이페이지로 이동
	@GetMapping(value="/mypage")
	public String mypage() {
		return "member/mypage";
	}
}
