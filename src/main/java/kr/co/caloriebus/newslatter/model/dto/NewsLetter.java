package kr.co.caloriebus.newslatter.model.dto;

import java.util.List;

import kr.co.caloriebus.board.model.dto.BoardComment;
import kr.co.caloriebus.board.model.dto.BoardFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsLetter {
	private int boardNo;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private int readCount;
	private int memberNo;
	private String regDate;
	private List<BoardFile> fileList;
	private List<BoardComment> boardCommentList;
	private int rNum;
	private String boardCategory;
	private int likeCount;
	private int isLike;
	private int commentCount;
}
