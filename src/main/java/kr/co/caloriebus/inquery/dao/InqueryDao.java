package kr.co.caloriebus.inquery.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.inquery.dto.InqueryCommentRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryFileRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryRowMapper;

@Repository
public class InqueryDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private InqueryRowMapper inqueryRowMapper;
	@Autowired
	private InqueryFileRowMapper inqueryFileRowMapper;
	@Autowired
	private InqueryCommentRowMapper inqueryCommentRowMapper;

	public List selectInqueryList(int start, int end) {
		String query ="select * from (select rownum as rnum ,n.* from (select * from inquery order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, inqueryRowMapper, params);
		System.out.println(list);
		return list;
	}

	public int selectInqueryTotalCount() {
		String query = "select count(*) from inquery";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
}
