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
	
	public List selectBoardList(String category, int start, int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category=? order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {category,start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}
	public List selectBoardList(int start,int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('B1','B2','B3','B4') order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}

	public int selectBoardTotalCount(String category) {
		String query = "select count(*) from board where board_category=?";
		Object[] params = {category};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	
	public int selectBoardTotalCount() {
		String query = "select count(*) from board where board_category in (?,?,?,?)";
		Object[] params = {"B1","B2","B3","B4"};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
}
