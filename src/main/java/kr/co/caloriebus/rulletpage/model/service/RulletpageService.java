package kr.co.caloriebus.rulletpage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.rulletpage.model.dao.RulletPageDao;

@Service
public class RulletpageService {
	@Autowired
	private RulletPageDao rulletpageDao;

}
