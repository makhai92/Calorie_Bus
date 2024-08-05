package kr.co.caloriebus.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {

		@GetMapping("/attendance/attendance")
		public String Attendance(){
			return "attendance/attendance";
		}
}
