package kr.co.caloriebus.member.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member m = new Member();
		m.setMemberAddr(rs.getString("member_addr"));
		m.setMemberBirth(rs.getString("member_birth"));
		m.setMemberEmail(rs.getString("member_email"));
		m.setMemberId(rs.getNString("member_id"));
		m.setMemberLevel(rs.getInt("member_level"));
		m.setMemberName(rs.getString("member_name"));
		m.setMemberNo(rs.getInt("member_no"));
		m.setMemberPhone(rs.getString("member_phone"));
		m.setMemberPw(rs.getString("member_pw"));
		m.setMemberAccount(rs.getInt("member_account"));
		m.setMemberBank(rs.getString("member_bank"));
		return m;
	}

}
