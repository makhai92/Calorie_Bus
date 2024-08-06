package kr.co.caloriebus.newslatter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsLetterComment {
	private int boardCommentNo;
	private String boardCommentContent;
	private int memberNo;
	private int boardRef;
	private int boardCommentRef;
	private String boardCommentDate;
	private String board_comment_writer;
	private int likeCount;
	private int isLike;
}
