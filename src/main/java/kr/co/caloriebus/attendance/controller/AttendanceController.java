package kr.co.caloriebus.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/attendance")
public class AttendanceController {

	@GetMapping("/attendance")
    public String attendancePage() {
        return "attendance/attendance";
    }
}
