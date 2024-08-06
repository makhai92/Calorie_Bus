package kr.co.caloriebus.faq.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FaqRowMapper implements RowMapper<Faq>{
	
	@Override
	public Faq mapRow(ResultSet rs, int rowNum) throws SQLException {
		Faq f = new Faq();
		System.out.println(f);
		f.setFaqNo(rs.getInt("faq_no"));
		f.setFaqTitle(rs.getString("faq_title"));
		f.setFaqContent(rs.getString("faq_content"));
		f.setMemberNo(rs.getInt("member_no"));
		return f;
	}
}
