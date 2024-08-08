package kr.co.caloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductReviewRowMapper implements RowMapper<ProductReview> {

	@Override
	public ProductReview mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductReview pr = new ProductReview();
		pr.setFundingNo(rs.getInt("funding_no"));
		pr.setMemberId(rs.getString("member_id"));
		pr.setReviewContent(rs.getString("review_content"));
		pr.setReviewImg(rs.getString("review_img"));
		pr.setProductNo(rs.getInt("product_no"));
		return pr;
	}

}
