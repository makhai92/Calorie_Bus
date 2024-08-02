package kr.co.coloriebus.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment>{

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment boardComment = new BoardComment();
		boardComment.setBoardCommentContent(rs.getString("board_comment_content"));
		boardComment.setBoardCommentDate(rs.getString("board_comment_date"));
		boardComment.setBoardCommentNo(rs.getInt("board_comment_no"));
		boardComment.setBoardCommentRef(rs.getInt("board_comment_ref"));
		boardComment.setBoardRef(rs.getInt("board_ref"));
		boardComment.setIsLike(rs.getInt("is_like"));
		boardComment.setLikeCount(rs.getInt("like_count"));
		boardComment.setMemberNo(rs.getInt("member_no"));
		boardComment.setBoard_comment_writer(rs.getString("board_comment_writer"));
		return boardComment;
	}
	
}
