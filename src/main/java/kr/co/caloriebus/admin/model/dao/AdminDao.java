package kr.co.caloriebus.admin.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.admin.model.dto.PurchaseRowMapper;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.member.model.dto.MemberRowMapper;
import kr.co.caloriebus.product.model.dto.Funding;



@Repository
public class AdminDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PurchaseRowMapper purchaseRowMapper;
	@Autowired
	private MemberRowMapper memberRowMapper;

	public List getAllFunding() {
		String query = "select * from funding order by 1";
		
		List list = jdbc.query(query, purchaseRowMapper);
		return list;
	}

	public int changeOrderState(Funding f) {
		String query = "update funding set order_state = ? where funding_no = ?";
		Object[] params = {f.getOrderState(),f.getFundingNo()};
		System.out.println(query);
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectAllMember() {
		String query = "select * from member order by 1";
		List list = jdbc.query(query , memberRowMapper);
		return list;
	}

	public int memberLevelChange(Member m) {
		String query = "update member set member_level = ? where member_no=?";
		Object[] params = {m.getMemberLevel(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}
}
