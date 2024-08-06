package kr.co.caloriebus.newslatter.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsLetterFileRowMapper implements RowMapper<NewsLetterFile>{

	@Override
	public NewsLetterFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsLetterFile newsletterFile = new NewsLetterFile();
		newsletterFile.setFileNo(rs.getInt("file_no"));
		newsletterFile.setFilepath(rs.getString("filepath"));
		newsletterFile.setFilename(rs.getString("filename"));
		newsletterFile.setBoardNo(rs.getInt("board_no"));
		return newsletterFile;
	}

}
