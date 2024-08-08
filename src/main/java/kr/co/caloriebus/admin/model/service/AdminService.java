package kr.co.caloriebus.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.caloriebus.admin.model.dao.AdminDao;
import kr.co.caloriebus.product.model.dto.Funding;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public List<Funding> getAllFunding() {
		List list = adminDao.getAllFunding();
		return list;
	}

	public int changeOrderState(Funding f) {
		int result = adminDao.changeOrderState(f);
		return result;
	}
}
