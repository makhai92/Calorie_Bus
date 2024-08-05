package kr.co.caloriebus.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardInfoRowMapper implements RowMapper<Board>{

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setBoardCategory(rs.getString("board_category"));
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardWriter(rs.getString("board_writer"));
		b.setMemberNo(rs.getInt("member_no"));
		b.setReadCount(rs.getInt("read_count"));
		b.setRegDate(rs.getString("reg_date"));
		b.setIsLike(rs.getInt("is_like"));
		b.setLikeCount(rs.getInt("like_count"));
		b.setCommentCount(rs.getInt("comment_count"));
		return b;
	}
	
}
