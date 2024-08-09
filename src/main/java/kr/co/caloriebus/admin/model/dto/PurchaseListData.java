package kr.co.caloriebus.admin.model.dto;

import java.util.List;

import kr.co.caloriebus.product.model.dto.Funding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseListData  {
	private List list;
	private String pageNavi;
}
