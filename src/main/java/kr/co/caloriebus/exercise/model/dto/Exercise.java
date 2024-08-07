package kr.co.caloriebus.exercise.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exercise {
	private int boardNo;			//게시판번호
	private String boardTitle;		//게시판제목
	private int memberNo;			//회원번호
	private String boardContent;	//게시판내용
	private String boardCategory;	//카테고리 목록 (I1=정보제공)
	private String boardWriter;		//작성자
	private int readCount;			//조회수
	private String regDate;			//작성일	
	private List<ExerciseFile> fileList;	
	private List<ExerciseComment> commentList;
	private List<ExerciseComment> reCommentList;
	private int Rnum;
}
