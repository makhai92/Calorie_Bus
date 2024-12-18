package kr.co.caloriebus.inquery.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.inquery.dto.Inquery;
import kr.co.caloriebus.inquery.dto.InqueryReplyRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryFile;
import kr.co.caloriebus.inquery.dto.InqueryFileRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryListRowMapper;
import kr.co.caloriebus.inquery.dto.InqueryReply;

@Repository
public class InqueryDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private InqueryRowMapper inqueryRowMapper;
	@Autowired
	private InqueryFileRowMapper inqueryFileRowMapper;
	@Autowired
	private InqueryReplyRowMapper inqueryReplyRowMapper;
	@Autowired
	private InqueryListRowMapper inqueryListRowMapper;
	
	
	public List selectInqueryList(int start, int end) {
		String query ="select * from (select rownum as rnum ,n.* from (select inquery_no,inquery_title,inquery_content,inquery_date,member_no,member_id as inquery_writer from inquery join member using(member_no) order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, inqueryRowMapper, params);
		return list;
	}

	public int selectInqueryTotalCount() {
		String query = "select count(*) from inquery";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public int selectInqueryNo() {
		String query = "select max(inquery_no) from inquery";
		int inqueryNo = jdbc.queryForObject(query,Integer.class);
		return inqueryNo;
	}

	public int insertInquery(Inquery i) {
		String query ="insert into inquery values(inquery_seq.nextval, ?, ?, to_char(sysdate, 'yyyy-mm-dd'),?)";
		Object[] params = {i.getInqueryTitle(),i.getInqueryContent(),i.getMemberNo()};
		int result = jdbc.update(query, params);
		return result;
		
	}
	
	public int insertInqueryFile(InqueryFile inqueryFile) {
		String query = "insert into inquery_file values(inquery_file_seq.nextval,?,?,?)";
		Object[] params = {inqueryFile.getFilePath(), inqueryFile.getFileName(),inqueryFile.getInqueryNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public Inquery selectOneInquery(int inqueryNo) {
		String query = "select inquery_no,inquery_title,inquery_content,inquery_date,member_no,member_id as inquery_writer from inquery join member using(member_no) where inquery_no = ?";
		Object[] params = {inqueryNo};
		List list = jdbc.query(query, inqueryRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			
			return (Inquery)list.get(0);
		}
	}

	public List selectInqueryFile(int inqueryNo) {
		String query = "select * from inquery_file where inquery_no=?";
		Object[] params = {inqueryNo};
		List list = jdbc.query(query, inqueryFileRowMapper, params);
		return list;
	}

	public List<InqueryReply> selectReplyList(int inqueryNo, int memberNo) {
		String query = "select * from reply where inquery_no = ?";
		Object[] params = {inqueryNo};
		List list = jdbc.query(query, inqueryReplyRowMapper, params);
		return list;
	}


	// 마이페이지 용
	public List selectMyInqueryList(int memberNo, int start, int end) {
		String query = "select * from (select rownum rnum, i.* from ((select inquery_no, inquery_title, reply_no, inquery_date, member_no from inquery left join reply using (inquery_no) where member_no = ?) order by 1 desc)i) where rnum between ? and ?";
		Object[] params = {memberNo, start, end};
		List list = jdbc.query(query, inqueryListRowMapper, params);
		return list;
	}
	
	public int selectMyInqueryTotalCount(int memberNo) {
		String query = "select count(*) from inquery where member_no = ?";
		Object[] params = {memberNo};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	public int insertReply(InqueryReply ir) {
		String query = "insert into Reply values(reply_seq.nextval ,?,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object[] params = {ir.getReplyContent(),ir.getInqueryRef()};	
		int result = jdbc.update(query,params);
		return result;
	}

	public int deleteReply(InqueryReply ir) {
		String query = "delete from reply where reply_no=?";
		Object[] params = {ir.getReplyNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int deleteInquery(int inqueryNo) {
		String query = "delete from inquery where inquery_no=?";
		Object[] params = {inqueryNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int updateReply(InqueryReply ir) {
		String query = "update reply set reply_content=? where reply_no=?";
		Object [] params = {ir.getReplyContent(), ir.getReplyNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int inqueryUpdate(Inquery i) {
		String query = "update inquery set inquery_title=?, inquery_content=? where inquery_no=?";
		Object[] params = {i.getInqueryTitle(),i.getInqueryContent(), i.getInqueryNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public InqueryFile selectOneInqueryFile(int fileNo) {
		String query = "select * from inquery_file where file_no=?";
		Object[] params = {fileNo};
		List list = jdbc.query(query, inqueryFileRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}
		return (InqueryFile)list.get(0);
	}

	public int deleteInqueryFile(int fileNo) {
		String query = "delete from inquery_file where file_no=?";
		Object[] params = {fileNo};
		int result = jdbc.update(query,params);
		return result;
	}
	/*
	public List selectReplyCount(int inqueryNo) {
		String query = "select inquery_no from reply where reply_content is not null";
		List list = jdbc.query(query);
	return list;
	}
	*/

	public List<Integer> selectReplyCount(int inqueryNo) {
		 String query = "SELECT inquery_no FROM reply WHERE reply_content IS NOT NULL AND inquery_no = ?";
		    return jdbc.query(query, new Object[]{inqueryNo}, (rs, rowNum) -> rs.getInt("inquery_no"));
	}

	
}

	

	
	

