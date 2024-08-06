package kr.co.caloriebus.newsletter.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterCommentRowMapper;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFile;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFileRowMapper;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterInfoRowMapper;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterListRowMapper;

@Repository
public class NewsLetterDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private NewsLetterInfoRowMapper newsletterInfoRowMapper;
	
	@Autowired
	private NewsLetterListRowMapper newsletterListRowMapper;

	public List selectNewsLetterList(String category, int start, int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category=? order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {category,start,end};
		List list = jdbc.query(query, newsletterListRowMapper,params);
		return list;
	}
	
	public List selectNewsLetterList(int start, int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('B1','B2','B3','B4') order by 1 desc)b))bb where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, newsletterListRowMapper,params);
		return list;
	}
	
	public int selectNewsLetterTotalCount(String category) {
		String query = "select count(*) from board where board_category=?";
		Object[] params = {category};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	public int selectNewsLetterTotalCount() {
		String query = "select count(*) from board where board_category in ('B1','B2','B3','B4')";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public int insertNewsLetter(NewsLetter nl) {
		String query = "insert into board values(board_seq.nextval,?,?,?,?,1,to_char(sysdate,'YYYY-MM-DD'))";
		System.out.println(nl);
		Object[] params = {nl.getMemberNo(),"N1",nl.getBoardTitle(),nl.getBoardContent()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectBoardNo() {
		String query = "select max(board_no) from board";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}

	public int insertNewsLetterFile(NewsLetterFile newsletterFile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = {newsletterFile.getFilename(),newsletterFile.getFilepath(),newsletterFile.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
}
