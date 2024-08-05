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
}
