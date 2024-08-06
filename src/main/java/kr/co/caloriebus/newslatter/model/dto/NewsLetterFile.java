package kr.co.caloriebus.newslatter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsLetterFile {
	private int fileNo;
	private String filename;
	private String filepath;
	private int boardNo;
}
