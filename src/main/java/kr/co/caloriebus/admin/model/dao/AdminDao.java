package kr.co.caloriebus.admin.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.admin.model.dto.PurchaseRowMapper;



@Repository
public class AdminDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private PurchaseRowMapper purchaseRowMapper;

	public List getAllFunding() {
		String query = "select * from funding order by 1";
		
		List list = jdbc.query(query, purchaseRowMapper);
		return list;
	}
}
