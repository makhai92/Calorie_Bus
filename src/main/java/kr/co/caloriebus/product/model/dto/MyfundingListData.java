package kr.co.caloriebus.product.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyfundingListData {
	private List list;
	private String pageNavi;
}
