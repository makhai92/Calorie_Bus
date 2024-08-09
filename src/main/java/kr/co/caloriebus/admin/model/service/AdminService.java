package kr.co.caloriebus.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.admin.model.dao.AdminDao;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.rulletpage.model.dto.RulletPage;

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

	public List getAllMember() {
		List list = adminDao.selectAllMember();
		return list;
	}
	
	public int memberLevelChange(Member m) {
		int result = adminDao.memberLevelChange(m);
		return result;
	}

	public List getAllDetails() {
		List list = adminDao.getAllDetails();
		return list;
	}

	public int eventStateUpdate(RulletPage r) {
		int result = adminDao.eventStateUpdate(r);
		return result;
	}
}
