package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseListRowMapper implements RowMapper<Exercise>{

	@Override
	public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
		   Exercise exercise = new Exercise();
	        exercise.setBoardNo(rs.getInt("board_no"));
	        exercise.setMemberNo(rs.getInt("member_no"));
	        exercise.setBoardTitle(rs.getString("board_title"));
	        exercise.setBoardCategory(rs.getString("board_category"));
	        exercise.setReadCount(rs.getInt("read_count"));
	        exercise.setRegDate(rs.getString("reg_date"));
	        exercise.setBoardWriter(rs.getString("board_writer"));
	        exercise.setCommentCount(rs.getInt("comment_count"));
	        exercise.setLikeCount(rs.getInt("like_count"));
	        return exercise;
	}
}
