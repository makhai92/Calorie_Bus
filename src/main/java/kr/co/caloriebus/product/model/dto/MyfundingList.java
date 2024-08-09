package kr.co.caloriebus.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyfundingList {
	private int fundingNo;
	private int memberNo;
	private int productNo;
	private String productTitle;
	private int productDcPrice;
	private String productImg;
	private String orderDate;
	private int orderState;
	private int orderAmount;
	private String reviewContent;
}
