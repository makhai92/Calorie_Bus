package kr.co.caloriebus.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mylike {
	private int productNo;
	private int productDcPrice;
	private int productPrice;
	private String productTitle;
	private String productImg;
	private String endDate;
}
