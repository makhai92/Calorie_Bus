package kr.or.caloriebus.inquery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.caloriebus.inquery.dao.InqueryDao;

@Service
public class InqueryService {
	@Autowired
	private InqueryDao inqueryDao;
}
