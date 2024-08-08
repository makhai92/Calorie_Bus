package kr.co.caloriebus.inquery.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InqueryReplyRowMapper implements RowMapper<InqueryReply> {
	
	@Override
	public InqueryReply mapRow(ResultSet rs, int rowNum) throws SQLException {
		InqueryReply ir = new InqueryReply();
		ir.setReplyNo(rs.getInt("reply_no"));
		ir.setReplyContent(rs.getString("reply_content"));
		ir.setReplyDate(rs.getString("reply_date"));
		ir.setInqueryNo(rs.getInt("inquery_no"));
		return ir;
		
	}
	
}
