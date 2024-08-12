package kr.co.caloriebus.rulletpage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.rulletpage.model.service.RulletPageService;



@Controller
@RequestMapping(value="/rulletPage")
public class RulletPageController {
	@Autowired
	private RulletPageService rulletPageService;
	
	@GetMapping("/rullet")
	public String rulletPage() {
		return "rulletPage/rullet";
	}
	@ResponseBody
	@PostMapping("/getRulletCount")
	public int getRulletCount(@SessionAttribute(required = false)Member member) {
		if(member != null) {
			int eventCount = rulletPageService.selectEventCount(member.getMemberNo());
			return eventCount;
		}else {
			return -10;
		}
	}
	@ResponseBody
	@PostMapping("/insertEventItem")
	public int insertEventItem(String eventItemName,@SessionAttribute(required = false)Member member) {
		if(member != null) {
			int result = rulletPageService.insertEventItem(eventItemName,member.getMemberNo());
			return result;
		}else {
			return -1;
		}
	}
}
