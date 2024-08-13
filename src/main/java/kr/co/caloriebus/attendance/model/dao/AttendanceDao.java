package kr.co.caloriebus.attendance.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.attendance.model.dto.AttendanceRowMapper;

@Repository
public class AttendanceDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private AttendanceRowMapper attendanceRowMapper;
	
	
}
