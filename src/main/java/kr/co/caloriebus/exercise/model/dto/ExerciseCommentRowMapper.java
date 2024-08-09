package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseCommentRowMapper implements RowMapper<ExerciseComment>{

	@Override
	public ExerciseComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExerciseComment exerciseComment = new ExerciseComment();
		exerciseComment.setBoardCommentNo(rs.getInt("board_comment_no"));
		exerciseComment.setBoardCommentContent(rs.getString("board_comment_content"));
		exerciseComment.setBoardCommentDate(rs.getString("board_comment_date"));
		exerciseComment.setMemberNo(rs.getInt("member_no"));
		exerciseComment.setBoardRef(rs.getInt("board_ref"));
		exerciseComment.setBoardCommentRef(rs.getInt("board_comment_ref"));
		exerciseComment.setIsLike(rs.getInt("is_like"));
		exerciseComment.setLikeCount(rs.getInt("like_count"));
		exerciseComment.setBoard_comment_writer(rs.getString("board_comment_writer"));
		exerciseComment.setReCommentCount(rs.getInt("re_comment_count"));
		return exerciseComment;
	}

}
