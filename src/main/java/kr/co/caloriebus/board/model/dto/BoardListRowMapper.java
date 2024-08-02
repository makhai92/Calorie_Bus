package kr.co.caloriebus.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardListRowMapper implements RowMapper<Board>{

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setBoardCategory(rs.getString("board_category"));
		b.setBoardNo(rs.getInt("board_no"));
		b.setMemberNo(rs.getInt("member_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardWriter(rs.getString("board_writer"));
		b.setReadCount(rs.getInt("read_count"));
		b.setRegDate(rs.getString("reg_date"));
		b.setLikeCount(rs.getInt("like_count"));
		b.setCommentCount(rs.getInt("comment_count"));
		return b;
	}

}
