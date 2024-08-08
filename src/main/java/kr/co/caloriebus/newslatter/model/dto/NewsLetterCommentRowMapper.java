package kr.co.caloriebus.newslatter.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsLetterCommentRowMapper implements RowMapper<NewsLetterComment>{

	@Override
	public NewsLetterComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsLetterComment comment = new NewsLetterComment();
		comment.setBoardCommentNo(rs.getInt("board_comment_no"));
		comment.setBoardCommentContent(rs.getString("board_comment_content"));
		comment.setBoardCommentDate(rs.getString("board_comment_date"));
		comment.setMemberNo(rs.getInt("member_no"));
		comment.setBoardRef(rs.getInt("board_ref"));
		comment.setBoardCommentRef(rs.getInt("board_comment_ref"));
		comment.setIsLike(rs.getInt("is_like"));
		comment.setLikeCount(rs.getInt("like_count"));
		comment.setBoard_comment_writer(rs.getString("board_comment_writer"));
		return comment;
	}
}
