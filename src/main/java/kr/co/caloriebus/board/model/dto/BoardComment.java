package kr.co.caloriebus.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {
	private int boardCommentNo;
	private String boardCommentContent;
	private int memberNo;
	private int boardRef;
	private int boardCommentRef;
	private String boardCommentDate;
	private String boardCommentWriter;
	private int isLike;
	private int likeCount;
	private int reCommentCount;
}
