package kr.co.caloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MyfundingRowMapper implements RowMapper<Myfunding>{

	@Override
	public Myfunding mapRow(ResultSet rs, int rowNum) throws SQLException {
		Myfunding myfunding = new Myfunding();
		myfunding.setFundingNo(rs.getInt("funding_no"));
		myfunding.setMemberNo(rs.getInt("member_no"));
		myfunding.setProductNo(rs.getInt("product_no"));
		myfunding.setProductTitle(rs.getString("product_title"));
		myfunding.setProductDcPrice(rs.getInt("product_dc_price"));
		myfunding.setOrderDate(rs.getString("order_date"));
		myfunding.setOrderState(rs.getInt("order_state"));
		myfunding.setOrderAmount(rs.getInt("order_amount"));
		myfunding.setFundingName(rs.getString("funding_name"));
		myfunding.setFundingPhone(rs.getString("funding_phone"));
		myfunding.setFundingAddr(rs.getString("funding_addr"));
		myfunding.setFundingPostcode(rs.getString("funding_postcode"));
		return myfunding;
	}
	
}
