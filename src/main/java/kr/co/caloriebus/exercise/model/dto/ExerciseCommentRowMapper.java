package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseCommentRowMapper implements RowMapper<ExerciseComment>{

	@Override
	public ExerciseComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExerciseComment ec = new ExerciseComment();
		ec.setBoardCommentNo(rs.getInt("board_comment_no"));
		ec.setBoardCommentContent(rs.getString("board_comment_content"));
		ec.setBoardCommentDate(rs.getString("board_comment_date"));
		ec.setMemberNo(rs.getInt("member_no"));
		ec.setBoardRef(rs.getInt("board_ref"));
		ec.setBoardCommentRef(rs.getInt("board_comment_ref"));
		ec.setIsLike(rs.getInt("is_like"));
		ec.setLikeCount(rs.getInt("like_count"));
		ec.setBoard_comment_writer(rs.getString("board_comment_writer"));
		ec.setReCommentCount(rs.getInt("re_comment_count"));
		return ec;
	}

}
