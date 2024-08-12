package kr.co.caloriebus.inquery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InqueryFile {
	private int fileNo;
	private int inqueryNo;
	private String filePath;
	private String fileName;
}
