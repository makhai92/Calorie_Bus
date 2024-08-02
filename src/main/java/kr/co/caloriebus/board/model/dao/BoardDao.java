package kr.co.caloriebus.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.board.model.dto.BoardInfoRowMapper;
import kr.co.caloriebus.board.model.dto.BoardListRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardInfoRowMapper boardInfoRowMapper;
	@Autowired
	private BoardListRowMapper boardListRowMapper;
	
	public List selectBoardList(String category) {
		
		return null;
	}
}
