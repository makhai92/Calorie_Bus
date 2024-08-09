package kr.co.caloriebus.newsletter.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardComment;
import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterComment;
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
	
	@Autowired
	private NewsLetterCommentRowMapper newsletterCommentRowMapper;

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
	public List selectNewsLetterCommentList(int boardNo,int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,"
				+ "to_char(board_comment_date,'yyyy-mm-dd')as board_comment_date,member_no from board_comment join member using (member_no) where board_ref=? and board_comment_ref is null order by 1)bc";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, newsletterCommentRowMapper,params);
		return list;
	}
	public List selectNewsLetterReCommentList(int boardNo,int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,"
				+ "to_char(board_comment_date,'yyyy-mm-dd')as board_comment_date,member_no from board_comment join member using (member_no) where board_ref=? and board_comment_ref is not null order by 1)bc";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, newsletterCommentRowMapper,params);
		return list;
	}
	public int insertNewsLetterComment(NewsLetterComment nlc) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,?,?,to_char(sysdate,'YYYY-MM-DD'))";
		String boardCommentRef = nlc.getBoardCommentRef() == 0 ? null : String.valueOf(nlc.getBoardCommentRef());
		Object[] params = {nlc.getBoardCommentContent(),nlc.getMemberNo(),nlc.getBoardRef(),boardCommentRef};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteNewsLetterCommentLike(int boardCommentNo, int memberNo) {
		String query = "delete from board_comment_like where board_comment_no=? and member_no=?";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query, params);
		return result;
	}
	public int insertNewsLetterCommentLike(int boardCommentNo, int memberNo) {
		String query = "insert into board_comment_like values(?,?)";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectNewsLetterCommentLikeCount(int boardCommentNo) {
		String query = "select count(*) from board_comment_like where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class,params);
		return likeCount;
	}
	
	public int updateNewsLetter(NewsLetter nl) {
        String query = "update board set board_title=?, board_content=? where board_no=?";
        Object[] params = {nl.getBoardTitle(), nl.getBoardContent(), nl.getBoardNo()};
        return jdbc.update(query, params);
    }

    public int deleteNewsLetter(int boardNo) {
        String query = "delete from board where board_no=?";
        Object[] params = {boardNo};
        return jdbc.update(query, params);
    }

    public int deleteNewsLetterFiles(int boardNo) {
        String query = "delete from board_file where board_no=?";
        Object[] params = {boardNo};
        return jdbc.update(query, params);
    }

	public List<NewsLetter> searchNewsLetterList(String keyword, int start, int end) {
		String query = "select * from (select rownum rnum, b.* from ("
                + "select board_no, member_no, board_title, board_category, read_count, reg_date, member_id as board_writer, "
                + "(select count(*) from board_comment where board_ref = board.board_no) as comment_count, "
                + "(select count(*) from board_like where board_no = board.board_no) as like_count "
                + "from board join member using(member_no) "
                + "where board_category = 'N1' and board_title like ? order by 1 desc)b)bb "
                + "where rnum between ? and ?";
        Object[] params = {"%" + keyword + "%", start, end};
        return jdbc.query(query, new NewsLetterListRowMapper(), params);
    }

    public int searchNewsLetterTotalCount(String keyword) {
        String query = "select count(*) from board where board_category = 'N1' and board_title like ?";
        return jdbc.queryForObject(query, Integer.class, "%" + keyword + "%");
    }

}
