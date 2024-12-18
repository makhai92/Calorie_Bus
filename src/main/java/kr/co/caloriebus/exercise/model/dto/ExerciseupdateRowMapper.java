package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseupdateRowMapper implements RowMapper<Exercise>{

	@Override
	public Exercise mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exercise e = new Exercise();
		e.setBoardCategory(rs.getString("board_category"));
		e.setBoardNo(rs.getInt("board_no"));
		e.setMemberNo(rs.getInt("member_no"));
		e.setBoardTitle(rs.getString("board_title"));
		e.setReadCount(rs.getInt("read_count"));
		e.setRegDate(rs.getString("reg_date"));
		return e;
	}

}
