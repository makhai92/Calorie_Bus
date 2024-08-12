package kr.co.caloriebus.rulletpage.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.rulletpage.model.dto.RulletPageRowMapper;

@Repository
public class RulletPageDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private RulletPageRowMapper rulletRowMapper;
	public int selectEventCount(int memberNo) {
		String query = "select event_count from member where member_no=?";
		Object[] params = {memberNo};
		int eventCount = jdbc.queryForObject(query, Integer.class,params);
		return eventCount;
	}
	public int updateEventCount(int memberNo) {
		String query = "update member set event_count=event_count-1 where member_no=?";
		Object[] params = {memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int insertEventItem(String eventItemName, int memberNo) {
		String query = "insert into event_item values(?,'N',?)";
		Object[] params = {memberNo,eventItemName};
		int result = jdbc.update(query,params);
		return result;
	}
}
