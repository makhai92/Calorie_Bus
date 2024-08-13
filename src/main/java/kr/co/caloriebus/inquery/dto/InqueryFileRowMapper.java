package kr.co.caloriebus.inquery.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InqueryFileRowMapper implements RowMapper<InqueryFile>{
	
	@Override
	public InqueryFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		InqueryFile inFile = new InqueryFile();
		inFile.setFileName(rs.getString("filename"));
		inFile.setFileNo(rs.getInt("file_no"));
		inFile.setFilePath(rs.getString("filepath"));
		inFile.setInqueryNo(rs.getInt("inquery_no"));
		return inFile;
	}
}
