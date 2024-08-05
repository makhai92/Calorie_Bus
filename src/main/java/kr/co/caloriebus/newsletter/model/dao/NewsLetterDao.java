package kr.co.caloriebus.newsletter.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.newslatter.model.dto.NewsLetterCommentRowMapper;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFileRowMapper;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterRowMapper;

@Repository
public class NewsLetterDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NewsLetterRowMapper newsletterRowMapper;
	@Autowired
	private NewsLetterFileRowMapper newsletterFileRowMapper;
	@Autowired
	private NewsLetterCommentRowMapper newsLetterCommentRowMapper;
}
