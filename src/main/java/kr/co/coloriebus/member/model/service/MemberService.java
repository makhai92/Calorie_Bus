package kr.co.coloriebus.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.coloriebus.member.model.dao.MemberDao;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
}
