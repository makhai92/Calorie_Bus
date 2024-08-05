package kr.co.caloriebus.member.model.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

	public Member selectOneMember(String memberId) {
		Member member = memberDao.selectOneMember(memberId);
		return member;
	}

	@Transactional
	public int insertMember(Member m) {
		int result = memberDao.insertMember(m);
		return result;
	}

	public Member selectOneMember(Member m) {
		Member member = memberDao.selectOneMember(m);
		return member;
	}

	public int verifyEmail(String memberEmail) {
		Random random = new Random();
		int veriCode = random.nextInt(888888) + 111111;
		String setFrom = "chae02100103@gmail.com";
		String title = "칼로리버스 회원가입 인증 이메일입니다.";
		String content = "인증 코드는 [" + veriCode + "]입니다." + 
						"<br>" + 
						"위 번호를 인증 코드 입력란에 입력해 주세요.";
		String toMail = memberEmail;
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
		return veriCode;
	}
}
