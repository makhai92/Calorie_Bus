package kr.co.caloriebus.inquery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InqueryListData {
	private List list;
	private String pageNavi;
}
