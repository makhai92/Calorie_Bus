package kr.co.caloriebus.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.member.model.dto.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	
	public Member selectOneMember(String memberId) {
		String query = "select * from member where member_id = ?";
		Object[] params = {memberId};
		List list = jdbc.query(query, memberRowMapper, params);
		if (list.isEmpty()) {
			return null;
		} else {
			return (Member)list.get(0);
		}
	}

	public int insertMember(Member m) {
		String query = "insert into member values(member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, 2, 0)";
		Object[] params = {m.getMemberId(), m.getMemberPw(), m.getMemberName(), m.getMemberPhone(), m.getMemberAddr(), m.getMemberEmail(), m.getMemberBirth()};
		int result = jdbc.update(query, params);
		return result;
	}

	public Member selectOneMember(Member m) {
		String query = "select * from member where member_id = ? and member_pw = ?";
		Object[] params = {m.getMemberId(), m.getMemberPw()};
		List list = jdbc.query(query, memberRowMapper, params);
		if (list.isEmpty()) {
			return null;
		} else {
			return (Member)list.get(0);
		}
	}

	// 이메일로 회원 찾기 (이메일 인증 용)
	public int selectMemberEmail(String memberEmail) {
		String query = "select count(*) from member where member_email = ?";
		Object[] params = {memberEmail};
		try {			
			int result = jdbc.queryForObject(query, Integer.class, params);
			return result; // 해당 이메일로 회원 있으면 1, 없으면 0 리턴
		}
		catch(final DataAccessException e) {
			return -1; // 에러 났으면 1 리턴
		}
	}
	
	public String findId(Member m) {
		String query = "select member_id from member where member_name = ? and member_email = ? and member_phone = ?";
		Object[] params = {m.getMemberName(), m.getMemberEmail(), m.getMemberPhone()};
		try {			
			String memberId = jdbc.queryForObject(query, String.class, params);
			return memberId;
		}
		catch(final DataAccessException e) {
			return null;
		}
	}

	public int findPw(Member m) {
		String query = "select member_no from member where member_id = ? and member_name = ? and member_email = ?";
		Object[] params = {m.getMemberId(), m.getMemberName(), m.getMemberEmail()};
		try {			
			int memberNo = jdbc.queryForObject(query, Integer.class, params);
			return memberNo;
		}
		catch(final DataAccessException e) {
			return -1;
		}
	}
}
