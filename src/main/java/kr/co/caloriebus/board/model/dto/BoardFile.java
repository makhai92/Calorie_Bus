package kr.co.caloriebus.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardFile {
	private int fileNo;
	private String filename;
	private String filepath;
	private int boardNo;
}
