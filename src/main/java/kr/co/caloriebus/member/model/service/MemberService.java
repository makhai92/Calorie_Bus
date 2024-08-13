package kr.co.caloriebus.member.model.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.member.model.dao.MemberDao;
import kr.co.caloriebus.member.model.dto.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
    private StringEncryptor stringEncryptor;
	

	// 아이디로 멤버 찾기 (중복 확인 용)
	public Member selectOneMember(String memberId) {
		Member member = memberDao.selectOneMember(memberId);
		return member;
	}

	// 회원 등록 (회원 가입 용)
	@Transactional
	public int insertMember(Member m) {
		String encryptedPw = stringEncryptor.encrypt(m.getMemberPw()); // 비밀번호 암호화
		m.setMemberPw(encryptedPw); // 암호화된 비밀번호 세팅
		int result = memberDao.insertMember(m);
		return result;
	}

	// 멤버 객체로 회원 찾기 (로그인 용)
	public Member selectOneMember(Member m) {
		String encryptedPw = stringEncryptor.encrypt(m.getMemberPw()); // 비밀번호 암호화
		m.setMemberPw(encryptedPw); // 암호화된 비밀번호 세팅
		Member member = memberDao.selectOneMember(m);
		return member;
	}

	// 이메일 인증 코드 발송
	public int verifyEmail(String memberEmail) {
		int result = memberDao.selectMemberEmail(memberEmail);
		
		// 해당 이메일로 회원 없을 때 veriCode 값 리턴
		if (result == 0) { 
			Random random = new Random();
			int veriCode = random.nextInt(888888) + 111111;
			String title = "[칼로리버스] 회원가입 인증 이메일입니다.";
			String content = "인증 코드는 [" + veriCode + "]입니다." + 
					"<br>" + 
					"위 번호를 인증 코드 입력란에 입력해 주세요.";
			String toMail = memberEmail;
			sendEmail(toMail, title, content);
			result = veriCode;
		}
		
		// 해당 이메일로 회원 이미 있을 때 0 값 리턴
		else if (result == 1){
			result = 0; 
		}
		
		// 에러났을 때 result 그대로 -1 리턴
		return result;
	}
	
	// 이메일 보내기
	public void sendEmail(String toMail, String title, String content) {
		String setFrom = "caloriebus2408@gmail.com";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	// 아이디, 비번 찾기 용 이메일 인증 보내기
	public int findVerifyEmail(String memberEmail) {
		int result = memberDao.selectMemberEmail(memberEmail);
		
		// 해당 이메일로 회원 있을 때 veriCode 값 리턴
		if (result > 0) { 
			Random random = new Random();
			int veriCode = random.nextInt(888888) + 111111;
			String title = "[칼로리버스] 인증 코드 이메일입니다.";
			String content = "인증 코드는 [" + veriCode + "]입니다." + 
					"<br>" + 
					"위 번호를 인증 코드 입력란에 입력해 주세요.";
			String toMail = memberEmail;
			sendEmail(toMail, title, content);
			result = veriCode;
		}
		// 해당 이메일로 회원 없을 때 0 리턴
		return result;
	}
	
	// 아이디 찾기
	public String findId(Member m) {
		String memberId = memberDao.findId(m);
		if (memberId != null) {
			String toMail = m.getMemberEmail();
			String title = "[칼로리버스] 아이디 찾기 이메일입니다.";
			String content = "회원님의 아이디는 [" + memberId + "]입니다.";
			sendEmail(toMail, title, content);
		}
		return memberId;
	}

	// 비밀번호 찾기
	public Member findPw(Member m) {
		Member member = memberDao.findPw(m);
		return member;
	}
	
	// 비밀번호 재설정
	@Transactional
	public int updatePw(int memberNo, String memberPw) {
		String encryptedPw = stringEncryptor.encrypt(memberPw); // 비밀번호 암호화
		int result = memberDao.updatePw(memberNo, encryptedPw);
		return result;
	}

	// 회원 정보 수정
	@Transactional
	public int updateMember(Member m) {
		int result = memberDao.updateMember(m);
		return result;
	}

	// 회원 삭제
	@Transactional
	public int deleteMember(Member member, String inputPw) {
		String encryptedPw = stringEncryptor.encrypt(inputPw); // 입력 받은 비밀번호 암호화
		if(member.getMemberPw().equals(encryptedPw)) {			
			int result = memberDao.deleteMember(member);
			return result; // 회원 삭제 성공 여부에 따라 0이나 1 리턴
		}
		else {
			return -1; // 비밀번호 불일치 시 -1 리턴
		}
	}


}
