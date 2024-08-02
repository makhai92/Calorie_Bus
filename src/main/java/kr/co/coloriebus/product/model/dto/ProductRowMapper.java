package kr.co.coloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product();
		p.setEndDate(rs.getString("end_date"));
		p.setProductContent(rs.getString("product_content"));
		p.setProductDcPrice(rs.getInt("product_dc_price"));
		p.setProductMaxCount(rs.getInt("product_max_count"));
		p.setProductMinCount(rs.getInt("product_min_count"));
		p.setProductNo(rs.getInt("product_no"));
		p.setProductPrice(rs.getInt("product_price"));
		p.setProductTitle(rs.getString("product_title"));
		p.setStartDate(rs.getString("start_date"));
		return p;
	}
	
}
