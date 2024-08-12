package kr.co.caloriebus.admin.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.caloriebus.member.model.dto.Member;

@Component
public class MemberListRowMapper implements RowMapper<Member>{

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
		m.setMemberAccount(rs.getString("member_account"));
		m.setMemberBank(rs.getString("member_bank"));
		m.setMemberPostcode(rs.getString("member_postcode"));
		m.setEventCount(rs.getInt("event_count"));
		return m;
	}
	
}
