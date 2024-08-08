package kr.co.caloriebus.exercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseComment {
	private int boardCommentNo;
	private String boardCommentContent;
	private int memberNo;
	private int boardRef;
	private int boardCommentRef;
	private String boardCommentDate;
	private String board_comment_writer;
	private int isLike;
	private int likeCount;
	private int reCommentCount;
}
