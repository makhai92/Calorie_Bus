package kr.co.caloriebus.inquery.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InqueryListRowMapper implements RowMapper<MyInquery>{
	
	@Override
	public MyInquery mapRow(ResultSet rs, int rowNum) throws SQLException {
		MyInquery i = new MyInquery();
		i.setInqueryNo(rs.getInt("inquery_no"));
		i.setInqueryTitle(rs.getString("inquery_title"));
		i.setReplyNo(rs.getInt("reply_no"));
		i.setInqueryDate(rs.getString("inquery_date"));
		i.setMemberNo(rs.getInt("member_no"));
		return i;
	}
}
