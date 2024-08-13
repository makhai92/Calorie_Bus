package kr.co.caloriebus.newslatter.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsLetterListRowMapper implements RowMapper<NewsLetter>{

	@Override
	public NewsLetter mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsLetter nl = new NewsLetter();
		nl.setBoardCategory(rs.getString("board_category"));
		nl.setBoardNo(rs.getInt("board_no"));
		nl.setMemberNo(rs.getInt("member_no"));
		nl.setBoardTitle(rs.getString("board_title"));
		nl.setBoardWriter(rs.getString("board_writer"));
		nl.setReadCount(rs.getInt("read_count"));
		nl.setRegDate(rs.getString("reg_date"));
		nl.setLikeCount(rs.getInt("like_count"));
		nl.setCommentCount(rs.getInt("comment_count"));
		return nl;
	}
}
