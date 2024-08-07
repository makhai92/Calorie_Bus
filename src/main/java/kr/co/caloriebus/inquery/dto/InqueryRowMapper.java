package kr.co.caloriebus.inquery.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InqueryRowMapper implements RowMapper<Inquery>{
	
	@Override
	public Inquery mapRow(ResultSet rs, int rowNum) throws SQLException {
		Inquery i = new Inquery();
		i.setInqueryNo(rs.getInt("inquery_no"));
		i.setInqueryTitle(rs.getString("inquery_title"));
		i.setInqueryContent(rs.getString("inquery_content"));
		i.setInqueryDate(rs.getString("inquery_date"));
		i.setMemberNo(rs.getInt("member_no"));
		return i;
	}
}
