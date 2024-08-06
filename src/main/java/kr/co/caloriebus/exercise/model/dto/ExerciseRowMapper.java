package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseRowMapper implements RowMapper<Exercise>{

	@Override
	public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exercise e = new Exercise();
		e.setBoardCategory(rs.getString("board_category"));	//카데고리 목록(I1)
		e.setBoardNo(rs.getInt("board_no"));	//게시판 번호
		e.setMemberNo(rs.getInt("member_no"));	//회원번호
		e.setBoardWriter(rs.getString("board_writer"));	//작성자
		e.setBoardTitle(rs.getString("board_title"));	//게시판 제목
		e.setBoardContent(rs.getString("board_content"));	//게시판 내용
		e.setReadCount(rs.getInt("read_count"));	//조회수
		e.setRegDate(rs.getString("reg_date"));		//작성일
		return e;
	}
	
}
