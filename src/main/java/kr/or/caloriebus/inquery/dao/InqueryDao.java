package kr.or.caloriebus.inquery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InqueryDao {
	@Autowired
	private JdbcTemplate jdbc;
}
