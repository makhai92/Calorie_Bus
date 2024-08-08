package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseInfoRowMapper implements RowMapper<Exercise>{

	@Override
	public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exercise ex = new Exercise() ;
		ex.setBoardCategory(rs.getString("board_category"));
		ex.setBoardContent(rs.getString("board_content"));
		ex.setBoardNo(rs.getInt("board_no"));
		ex.setBoardTitle(rs.getString("board_title"));
		ex.setBoardWriter(rs.getString("board_writer"));
		ex.setMemberNo(rs.getInt("member_no"));
		ex.setReadCount(rs.getInt("read_count"));
		ex.setRegDate(rs.getString("reg_date"));
		ex.setIsLike(rs.getInt("is_like"));
		ex.setLikeCount(rs.getInt("like_count"));
		return ex;
	}

}
