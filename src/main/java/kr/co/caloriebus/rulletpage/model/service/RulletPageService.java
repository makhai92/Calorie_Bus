package kr.co.caloriebus.rulletpage.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.rulletpage.model.dao.RulletPageDao;

@Service
public class RulletPageService {
	@Autowired
	private RulletPageDao rulletPageDao;

	@Transactional
	public int selectEventCount(int memberNo) {
		int eventCount = rulletPageDao.selectEventCount(memberNo);
		if(eventCount > 0) {
			int result = rulletPageDao.updateEventCount(memberNo);
			if(result == 0) {
				return -1;
			}
		}
		return eventCount;
	}

	public int insertEventItem(String eventItemName, int memberNo) {
		int result = rulletPageDao.insertEventItem(eventItemName,memberNo);
		if(result > 0) {
			return result;
		}else {
			return -1;
		}
	}

}
