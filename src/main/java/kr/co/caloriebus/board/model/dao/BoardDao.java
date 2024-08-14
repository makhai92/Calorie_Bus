package kr.co.caloriebus.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardComment;
import kr.co.caloriebus.board.model.dto.BoardCommentRowMapper;
import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.board.model.dto.BoardFileRowMapper;
import kr.co.caloriebus.board.model.dto.BoardInfoRowMapper;
import kr.co.caloriebus.board.model.dto.BoardListRowMapper;
import kr.co.caloriebus.board.model.dto.BoardRowMapper;


@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardInfoRowMapper boardInfoRowMapper;
	@Autowired
	private BoardListRowMapper boardListRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;
	@Autowired
	private BoardRowMapper boardRowMapper;
	
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
		String query = "select count(*) from board where board_category in ('B1','B2','B3','B4')";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	public int insertBoard(Board b) {
		String query = "insert into board values(board_seq.nextval,?,?,?,?,1,to_char(sysdate,'YYYY-MM-DD'))";
		Object[] params = {b.getMemberNo(),b.getBoardCategory(),b.getBoardTitle(),b.getBoardContent()};
		int result = jdbc.update(query, params);
		return result;
	}
	public int selectBoardNo() {
		String query = "select max(board_no) from board";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}
	public int insertBoardFile(BoardFile boardFile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = {boardFile.getFilename(),boardFile.getFilepath(),boardFile.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public Board selectBoard(int memberNo,int boardNo) {
		String query = "select board_no,member_no,board_category,board_title,board_content,read_count,reg_date,member_id as board_writer,(select count(*) from board_like where board_no=b.board_no) as like_count,(select count(*) from board_comment where board_ref=b.board_no) as comment_count,(select count(*) from board_like where board_no=b.board_no and member_no=?) as is_like from board b join member using(member_no) where board_no=?";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, boardInfoRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {			
			return (Board)list.get(0);
		}
	}
	public int deleteBoardLike(int boardNo, int memberNo) {
		String query = "delete from board_like where board_no=? and member_no=?";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int insertBoardLike(int boardNo, int memberNo) {
		String query = "insert into board_like values(?,?)";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectBoardLikeCount(int boardNo) {
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
	public List selectBoardFileList(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardFileRowMapper, params);
		return list;
	}
	public List selectBoardCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like,(select count(*) from board_comment where board_comment_ref=bc.board_comment_no) as re_comment_count from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,(case \r\n" + 
				"        when(trunc((sysdate - board_comment_date)*24*60)) < 60 \r\n" + 
				"        then (trunc((sysdate - board_comment_date)*24*60))||'분전'\r\n" + 
				"        when (trunc((sysdate - board_comment_date)*24)) < 24\r\n" + 
				"        then (trunc((sysdate - board_comment_date)*24))||'시간전'\r\n" + 
				"        else to_char(board_comment_date,'yyyy-mm-dd')\r\n" + 
				"       \r\n" + 
				"     end)as board_comment_date,member_no from board_comment join member using (member_no) where board_ref=? and board_comment_ref is null order by 1)bc";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, boardCommentRowMapper,params);
		return list;
	}
	public int insertBoardComment(BoardComment bc) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,?,?,sysdate)";
		String boardCommentRef = bc.getBoardCommentRef() == 0 ? null : String.valueOf(bc.getBoardCommentRef());
		Object[] params = {bc.getBoardCommentContent(),bc.getMemberNo(),bc.getBoardRef(),boardCommentRef};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteBoardCommentLike(int boardCommentNo, int memberNo) {
		String query = "delete from board_comment_like where board_comment_no=? and member_no=?";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int insertBoardCommentLike(int boardCommentNo, int memberNo) {
		String query = "insert into board_comment_like values(?,?)";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectBoardCommentLikeCount(int boardCommentNo) {
		String query = "select count(*) from board_comment_like where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class,params);
		return likeCount;
	}
	
	public List selectBoardReCommentList(int boardCommentNo, int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like,(select count(*) from board_comment where board_comment_ref=bc.board_comment_no) as re_comment_count from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,(case \r\n" + 
				"        when(trunc((sysdate - board_comment_date)*24*60)) < 60 \r\n" + 
				"        then (trunc((sysdate - board_comment_date)*24*60))||'분전'\r\n" + 
				"        when (trunc((sysdate - board_comment_date)*24)) < 24\r\n" + 
				"        then (trunc((sysdate - board_comment_date)*24))||'시간전'\r\n" + 
				"        else to_char(board_comment_date,'yy-mm-dd')\r\n" + 
				"       \r\n" + 
				"     end)as board_comment_date,member_no from board_comment join member using (member_no) where board_comment_ref=? order by 1)bc";
		Object[] params = {memberNo,boardCommentNo};
		List list = jdbc.query(query, boardCommentRowMapper,params);
		return list;
	}
	public List selectBoardTitleList(int start, int end, String keyword) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('B1','B2','B3','B4') and board_title like '%'||?||'%' order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {keyword,start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}
	public List selectBoardTitleList(String category, int start, int end, String keyword) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category=? and board_title like '%'||?||'%' order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {category,keyword,start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}
	public int selectBoardTitleTotalCount(String keyword) {
		String query = "select count(*) from board where board_category in ('B1','B2','B3','B4') and board_title like '%'||?||'%'";
		Object[] params = {keyword};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
	public int selectBoardTotalTitleCount(String category, String keyword) {
		String query = "select count(*) from board where board_category=? and board_title like '%'||?||'%'";
		Object[] params = {category,keyword};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	public List selectBoardWriterList(int start, int end, String keyword) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('B1','B2','B3','B4') and member_id like '%'||?||'%' order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {keyword,start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}
	public List selectBoardWriterList(String category, int start, int end, String keyword) {
		String query = "select * from (select rownum rnum, b.*\r\n" + 
				"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category=? and member_id like '%'||?||'%' order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {category,keyword,start,end};
		List list = jdbc.query(query, boardListRowMapper,params);
		return list;
	}
	public int selectBoardWriterTotalCount(String keyword) {
		String query = "select count(*) from board join member using(member_no) where board_category in ('B1','B2','B3','B4') and member_id like '%'||?||'%'";
		Object[] params = {keyword};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
	public int selectBoardTotalWriterCount(String category, String keyword) {
		String query = "select count(*) from board join member using(member_no) where board_category=? and member_id like '%'||?||'%'";
		Object[] params = {category,keyword};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	
	// 마이페이지 용
	public List selectMyBoardList(int memberNo, int start, int end) {
		String query = "select * from (select rownum rnum, b.* from ((select board_no, member_no, board_title, board_category, read_count, reg_date, member_id as board_writer, (select count(*) from board_comment where board_ref=board.board_no) as comment_count, (select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category in ('B1','B2','B3','B4') and member_no=? order by 1 desc)b)) where rnum between ? and ?";
		Object[] params = {memberNo, start, end};
		List list = jdbc.query(query, boardListRowMapper, params);
		return list;
	}
	
	public int selectMyBoardTotalCount(int memberNo) {
		String query = "select count(*) from board where member_no = ?";
		Object[] params = {memberNo};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	public int updateComment(BoardComment bc) {
		String query = "update board_comment set board_comment_content=? where board_comment_no=?";
		Object[] params = {bc.getBoardCommentContent(),bc.getBoardCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteComment(int boardCommentNo) {
		String query = "delete from board_comment where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteBoard(int boardNo, int memberNo) {
		String query = "delete from board where board_no=? and member_no=?";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public Board selectOneBoard(int boardNo) {
		String query = "select board_content,board_no,board_title,board_category,member_id as board_writer,reg_date from board join member using(member_no) where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query,boardRowMapper ,params);
		return (Board)list.get(0);
	}
	public int updateBoard(Board b) {
		String query = "update board set board_title=?, board_content=?,board_category=? where board_no=?";
		Object[] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardCategory(),b.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public BoardFile selectOneBoardFile(int fileNo) {
		String query = "select * from board_file where file_no=?";
		Object[] params = {fileNo};
		List list = jdbc.query(query, boardFileRowMapper,params);
		return (BoardFile)list.get(0);
	}
	public int deleteBoardFile(int fileNo) {
		String query = "delete from board_file where file_no=?";
		Object[] params = {fileNo};
		int result = jdbc.update(query,params);
		return result;
	}
}
