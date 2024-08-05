package kr.co.caloriebus.rulletpage.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RulletPageRowMapper implements RowMapper<RulletPage>{

	@Override
	public RulletPage mapRow(ResultSet rs, int rowNum) throws SQLException {
	RulletPage rp = new RulletPage();
	rp.setMemberNo(rs.getInt("member_no"));
	rp.setEventState(rs.getString("event_state"));
	rp.setEventitemName(rs.getString("event_item_name"));
		return rp;
	}

}
