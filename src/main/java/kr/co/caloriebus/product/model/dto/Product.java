package kr.co.caloriebus.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
	private int productNo;
	private String productTitle;
	private String productContent;
	private int productPrice;
	private int productDcPrice;
	private int productMinCount;
	private int productMaxCount;
	private String startDate;
	private String endDate;
}
