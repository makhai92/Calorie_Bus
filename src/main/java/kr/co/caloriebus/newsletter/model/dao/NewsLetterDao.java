package kr.co.caloriebus.newsletter.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.board.model.dto.Board;
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
	
	@Autowired
	private NewsLetterFileRowMapper newsletterFileRowMapper;

	public List selectNewsLetterList(int start, int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category='N1' order by 1 desc)b))bb where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, newsletterListRowMapper,params);
		return list;
	}
	
	public int selectNewsLetterTotalCount() {
		String query = "select count(*) from board where board_category='N1'";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public int insertNewsLetter(NewsLetter nl) {
		String query = "insert into board values(board_seq.nextval,?,'N1',?,?,1,to_char(sysdate,'YYYY-MM-DD'))";
		System.out.println(nl);
		Object[] params = {nl.getMemberNo(),nl.getBoardTitle(),nl.getBoardContent()};
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
	public NewsLetter selectNewsLetter(int memberNo,int boardNo) {
		String query = "select board_no,member_no,board_category,board_title,board_content,read_count,reg_date,member_id as board_writer,(select count(*) from board_like where board_no=b.board_no) as like_count,(select count(*) from board_comment where board_ref=b.board_no) as comment_count,(select count(*) from board_like where board_no=b.board_no and member_no=?) as is_like from board b join member using(member_no) where board_no=?";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, newsletterInfoRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {			
			return (NewsLetter)list.get(0);
		}
	}
	public int deleteNewsLetterLike(int boardNo, int memberNo) {
		String query = "delete from board_like where board_no=? and member_no=?";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int insertNewsLetterLike(int boardNo, int memberNo) {
		String query = "insert into board_like values(?,?)";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectNewsLetterLikeCount(int boardNo) {
		String query = "select count(*) from board_like where board_no=?";
		Object[] params = {boardNo};
		int likeCount = jdbc.queryForObject(query, Integer.class,params);
		return likeCount;
	}
	public int updateReadCount(int boardNo) {
		String query = "update board set read_count=read_count+1 where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public List selectNewsLetterFileList(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, newsletterFileRowMapper, params);
		return list;
	}
}
