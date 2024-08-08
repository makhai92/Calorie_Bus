package kr.co.caloriebus.newslatter.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsLetterInfoRowMapper implements RowMapper<NewsLetter>{

	@Override
	public NewsLetter mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsLetter nl = new NewsLetter();
		nl.setBoardCategory(rs.getString("board_category"));
		nl.setBoardContent(rs.getString("board_content"));
		nl.setBoardNo(rs.getInt("board_no"));
		nl.setBoardTitle(rs.getString("board_title"));
		nl.setBoardWriter(rs.getString("board_writer"));
		nl.setMemberNo(rs.getInt("member_no"));
		nl.setReadCount(rs.getInt("read_count"));
		nl.setRegDate(rs.getString("reg_date"));
		nl.setIsLike(rs.getInt("is_like"));
		nl.setLikeCount(rs.getInt("like_count"));
		return nl;
	}
}
