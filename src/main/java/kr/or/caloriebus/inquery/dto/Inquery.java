package kr.or.caloriebus.inquery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Inquery {
	private int inqueryNo;
	private String inqueryTitle;
	private String inqueryContent;
	private String inqueryDate;
	private int number;
}
