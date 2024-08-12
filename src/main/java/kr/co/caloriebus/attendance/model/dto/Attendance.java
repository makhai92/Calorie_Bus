package kr.co.caloriebus.attendance.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendance {
	private int member_no;
	private String attendance_date;
}
