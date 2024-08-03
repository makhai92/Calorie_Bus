package kr.co.caloriebus.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
