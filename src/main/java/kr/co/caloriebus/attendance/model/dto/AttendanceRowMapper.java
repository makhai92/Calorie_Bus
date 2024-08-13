package kr.co.caloriebus.attendance.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AttendanceRowMapper implements RowMapper<Attendance>{

	@Override
	public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attendance a = new Attendance();
		a.setMember_no(rs.getInt("member_no"));
		a.setAttendance_date(rs.getString("attendance_date"));
		return a;
	}

}
