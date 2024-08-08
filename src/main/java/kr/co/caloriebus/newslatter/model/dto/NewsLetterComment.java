package kr.co.caloriebus.newslatter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsLetterComment {
	private int boardCommentNo;
	private String boardCommentWriter;
	private String boardCommentContent;
	private String boardCommentDate;
	private int boardRef;
	private int boardCommentRef;
	private int likeCount;
	private int isLike;
	private int memberNo;
}
