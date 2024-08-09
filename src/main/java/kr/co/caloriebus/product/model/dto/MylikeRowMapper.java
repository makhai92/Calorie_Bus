package kr.co.caloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MylikeRowMapper implements RowMapper<Mylike> {

	@Override
	public Mylike mapRow(ResultSet rs, int rowNum) throws SQLException {
		Mylike mylike = new Mylike();
		mylike.setProductNo(rs.getInt("product_no"));
		mylike.setProductDcPrice(rs.getInt("product_dc_price"));
		mylike.setProductPrice(rs.getInt("product_price"));
		mylike.setProductTitle(rs.getString("product_title"));
		mylike.setProductImg(rs.getString("product_img"));
		mylike.setEndDate(rs.getString("end_date"));
		return mylike;
	}

}
