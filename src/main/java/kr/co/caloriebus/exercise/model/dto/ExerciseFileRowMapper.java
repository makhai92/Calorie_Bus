package kr.co.caloriebus.exercise.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseFileRowMapper implements RowMapper<ExerciseFile>{

	@Override
	public ExerciseFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExerciseFile exerciseFile = new ExerciseFile();
		exerciseFile.setBoardNo(rs.getInt("board_no"));
		exerciseFile.setFilename(rs.getString("filename"));
		exerciseFile.setFileNo(rs.getInt("file_no"));
		exerciseFile.setFilepath(rs.getString("filepath"));
		return exerciseFile;
	}

}
