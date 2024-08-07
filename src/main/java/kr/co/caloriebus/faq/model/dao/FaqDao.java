package kr.co.caloriebus.faq.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.faq.model.dto.Faq;
import kr.co.caloriebus.faq.model.dto.FaqRowMapper;

@Repository
public class FaqDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private FaqRowMapper faqRowMapper;

	public int insertFaq(Faq f) {
		String query = "insert into faq values(faq_seq.nextval , ?, ?, ? )";
		Object[] params =  {f.getFaqTitle(),f.getFaqContent(),f.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectAllFaq(int start, int end) {
		String query = "select * from (select rownum as rnum ,n.* from (select * from faq order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, faqRowMapper,params);
		return list;
	}

	public int selectAllFaqCount() {
		String query = "select count(*) from faq";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public Faq selectOneFaq(int faqNo) {
		String query = "select * from faq where faq_no = ?";
		Object[] params = {faqNo};
		List list = jdbc.query(query, faqRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Faq) list.get(0);
		}

	}

	public int deleteFaq(int faqNo) {
		String query = "delete from faq where faq_no=?";
		Object[] params = {faqNo};
		int result = jdbc.update(query,params);
		
		return result;
	}
}
