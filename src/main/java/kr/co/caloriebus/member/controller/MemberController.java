package kr.co.caloriebus.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		int result = memberService.verifyEmail(memberEmail);
		// 회원 없을 때 veriCode, 있을 때 0, 에러났을 때 -1
		return result;
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
	
	// 아이디, 비번 찾기 용 이메일 인증 보내기
	@ResponseBody
	@PostMapping(value="/findVerifyEmail")
	public int findVerifyEmail(String memberEmail) {
		int result = memberService.findVerifyEmail(memberEmail);
		// 회원 있을 때 veriCode, 없을 때 0 리턴
		return result;
	}
	
	// 아이디 찾기
	@PostMapping(value="/findId")
	public String findId(Member m, Model model) {
		String memberId = memberService.findId(m);
		Message data = new Message();
		if(memberId != null) {
			data.setMessage("아이디가 이메일로 전송되었습니다.");
			data.setRedirectUrl("/member/loginForm");
		}
		else {			
			data.setMessage("일치하는 회원을 찾을 수 없습니다.");
			data.setRedirectUrl("/member/forgotId");
		}
		return alertMsg(data, model);
	}
	
	// 비밀번호 찾기 페이지로 이동
	@GetMapping(value="/forgotPw")
	public String forgotPw() {
		return "member/forgotPw";
	}
	
	// 비밀번호 재설정 가능한지 확인
	@PostMapping(value="/findPw")
	public String findPw(Member m, RedirectAttributes redirect, Model model) {
		int memberNo = memberService.findPw(m);
		if (memberNo > 0) {
			redirect.addAttribute("memberNo", memberNo);
			return "redirect:/member/resetPw";
		}
		else {
			Message data = new Message();
			data.setMessage("일치하는 회원을 찾을 수 없습니다.");
			data.setRedirectUrl("/member/forgotPw");
			return alertMsg(data, model);
		}
	}
	
	// 비밀번호 재설정 페이지로 이동
	@GetMapping(value="/resetPw")
	public String resetPw(@RequestParam("memberNo") int memberNo, Model model) {
		model.addAttribute("memberNo", memberNo);
		return "member/resetPw";
	}

	// 마이페이지로 이동
	@GetMapping(value="/mypage")
	public String mypage() {
		return "member/mypage";
	}
}
