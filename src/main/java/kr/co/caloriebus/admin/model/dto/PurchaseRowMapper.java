package kr.co.caloriebus.admin.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.caloriebus.product.model.dto.Funding;

@Component
public class PurchaseRowMapper implements RowMapper<Funding> {
	
	@Override
	public Funding mapRow(ResultSet rs, int rowNum) throws SQLException {
		Funding f = new Funding();
		f.setFundingNo(rs.getInt("funding_no"));
		f.setProductNo(rs.getInt("product_no"));
		f.setMemberNo(rs.getInt("member_no"));
		f.setOrderDate(rs.getString("order_date"));
		f.setOrderState(rs.getInt("order_state"));
		f.setOrderAmount(rs.getInt("order_amount"));
		f.setFundingName(rs.getString("funding_name"));
		f.setFundingPhone(rs.getString("funding_phone"));
		f.setFundingAddr(rs.getString("funding_addr"));
		f.setFundingPostcode(rs.getString("funding_postcode"));
		
		return f;
	}
}
