package kr.co.caloriebus.faq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FaqSearch {
	private String searchField;
	private String keyword;
}
