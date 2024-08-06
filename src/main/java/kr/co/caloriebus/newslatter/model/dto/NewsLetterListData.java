package kr.co.caloriebus.newslatter.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsLetterListData {
	private List list;
	private String pageNavi;
}
