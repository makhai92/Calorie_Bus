package kr.co.caloriebus.newslatter.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsLetter {
	private int boardNo;
	private String boardTitle;
	private String memberNo;
	private String boardContent;
	private int readCount;
	private String regDate;
	private List<NewsLetterFile> fileList;
	private List<NewsLetterComment> commentList;
	private List<NewsLetterComment> reCommentList;
}
