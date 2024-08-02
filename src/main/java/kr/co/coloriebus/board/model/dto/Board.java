package kr.co.coloriebus.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	private int boardNo;
	private int memberNo;
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private int readCount;
	private String regDate;
	private String boardWriter;
}
