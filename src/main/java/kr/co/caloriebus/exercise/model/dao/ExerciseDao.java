package kr.co.caloriebus.exercise.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.exercise.model.dto.Exercise;
import kr.co.caloriebus.exercise.model.dto.ExerciseCommentRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseFile;
import kr.co.caloriebus.exercise.model.dto.ExerciseFileRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseRowMapper;

@Repository
public class ExerciseDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ExerciseRowMapper exerciseRowMapper;
	@Autowired
	private ExerciseFileRowMapper exerciseFileRowMapper;
	@Autowired
	private ExerciseCommentRowMapper exerciseCommentRowMapper;

	public List selectBoardList(String category, int start, int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category=? order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {category,start,end};
		List list = jdbc.query(query, exerciseRowMapper,params);
		return list;
	}
	
	public List selectBoardList(int start,int end) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('I1') order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, exerciseRowMapper,params);
		return list;
	}

	public int selectBoardTotalCount(String category) {
		String query = "select count(*) from board where board_category=?";
		Object[] params = {category};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	
	public int selectBoardTotalCount() {
		String query = "select count(*) from board where board_category in ?";
		Object[] params = {"I1"};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	public int insertBoard(Exercise e) {
		String query = "insert into board values(board_seq.nextval,?,'I1',?,?,1,to_char(sysdate,'YYYY-MM-DD'))";
		Object[] params = {e.getMemberNo(),e.getBoardTitle(),e.getBoardContent()};
		int result = jdbc.update(query, params);
		return result;
	}
	public int selectBoardNo() {
		String query = "select max(board_no) from board";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}
	public int insertBoardFile(ExerciseFile exerciseFile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = {exerciseFile.getFilename(),exerciseFile.getFilepath(),exerciseFile.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	//운동정보
	public Exercise selectOneBoard(int boardNo) {
		String query = "select * from board where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseRowMapper, params);
		if(list.isEmpty()) {
			return null;			
		}
		return (Exercise)list.get(0);
	}
	public int updateReadCount(int boardNo) {
		String query = "update board set read_count = read_count+1 where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public List selectBoardfile(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseFileRowMapper, params);
		return list;
	}
	public List selectReCommentList(int boardNo, int memberNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseFileRowMapper,params);
		return list;
	}
	/*
	public List selectCommentList(int boardNo, int memberNo) {
		String query =  "select\r\n" + 
				"   nc.*,\r\n" + 
				"    (select count(*) from board_comment_like where board_comment_no=ec.board_comment_no) as like_count,\r\n" + 
				"    (select count(*) from board_comment_like where board_comment_no=ec.board_comment_no and member_no=?) as is_like\r\n" + 
				"from board_comment nc\r\n" + 
				"where board_ref=? and board_comment_ref is null order by 1";
		return null;
	}
	 */
	
	
}
