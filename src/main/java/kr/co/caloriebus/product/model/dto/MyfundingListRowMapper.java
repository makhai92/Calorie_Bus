package kr.co.caloriebus.product.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MyfundingListRowMapper implements RowMapper<MyfundingList> {

	@Override
	public MyfundingList mapRow(ResultSet rs, int rowNum) throws SQLException {
		MyfundingList myfundingList = new MyfundingList();
		myfundingList.setFundingNo(rs.getInt("funding_no"));
		myfundingList.setMemberNo(rs.getInt("member_no"));
		myfundingList.setProductNo(rs.getInt("product_no"));
		myfundingList.setProductTitle(rs.getString("product_title"));
		myfundingList.setProductDcPrice(rs.getInt("product_dc_price"));
		myfundingList.setProductImg(rs.getString("product_img"));
		myfundingList.setOrderDate(rs.getString("order_date"));
		myfundingList.setOrderState(rs.getInt("order_state"));
		myfundingList.setOrderAmount(rs.getInt("order_amount"));
		myfundingList.setReviewContent(rs.getString("review_content"));
		return myfundingList;
	}

}
