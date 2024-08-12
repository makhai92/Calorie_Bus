package kr.co.caloriebus.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductReview {
	private int fundingNo;
	private	String reviewContent;
	private String reviewImg;
	private String memberId;
	private int productNo;
	private int state;
}
