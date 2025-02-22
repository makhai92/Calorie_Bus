package kr.co.caloriebus.exercise.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.exercise.model.dto.Exercise;
import kr.co.caloriebus.exercise.model.dto.ExerciseComment;
import kr.co.caloriebus.exercise.model.dto.ExerciseCommentRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseFile;
import kr.co.caloriebus.exercise.model.dto.ExerciseFileRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseInfoRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseListRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseRowMapper;
import kr.co.caloriebus.exercise.model.dto.ExerciseupdateRowMapper;
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
	@Autowired
	private ExerciseInfoRowMapper exerciseInfoRowMapper;
	@Autowired
	private ExerciseupdateRowMapper exerciseupdateRowMapper;
	@Autowired
	private ExerciseListRowMapper exerciseListRowMapper;
	
	
	public List selectBoardList(int start, int end) {
		//System.out.println(start);
		String query = "select * from (select rownum rnum, b. *\r\n" + 
						"from ((select board_no,member_no,board_title,board_category,read_count,reg_date,member_id as board_writer,(select count(*) from board_comment where board_ref=board.board_no) as comment_count,(select count(*) from board_like where board_no=board.board_no) as like_count from board join member using(member_no) where board_category='I1' order by 1 desc)b))bb where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,exerciseRowMapper, params);
		return list;
	}

	public int selectBoardTotalCount() {
		String query = "select count(*) from board where board_category='I1'";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	//등록 
	public int insertExercise(Exercise e) {
		String query = "insert into board values(board_seq.nextval,?,'I1',?,?,1,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {e.getMemberNo(),e.getBoardTitle(),e.getBoardContent()};
		int result = jdbc.update(query,params);
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

	public Exercise selectExercise(int memberNo, int boardNo) {
		String query = "select board_no,member_no,board_category,board_title,board_content,read_count,reg_date,member_id as board_writer,(select count(*) from board_like where board_no=b.board_no) as like_count,(select count(*) from board_comment where board_ref=b.board_no) as comment_count,(select count(*) from board_like where board_no=b.board_no and member_no=?) as is_like from board b join member using(member_no) where board_no=?";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, exerciseInfoRowMapper, params);
		if(list.isEmpty()) {
			return null;			
		}else {
			return (Exercise)list.get(0);
		}
	}

	public int updateReadCount(int boardNo) {
		String query = "update board set read_count=read_count+1 where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params); 
		return result;
	}

	public List selectExerciseList(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseFileRowMapper, params);
		return list;
	}

	public int deleteExerciseLike(int boardNo, int memberNo) {
		String query = "delete from board_like where board_no=? and member_no=?";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertExerciseLike(int boardNo, int memberNo) {
		String query = "insert into board_like values(?,?)";
		Object[] params = {boardNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int selectExerciseLikeCount(int boardNo) {
		String query = "select count(*) from board_like where board_no=?";
		Object[] params = {boardNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
	
	//좋아요	
	public int deleteExerciseCommentLike(int boardCommentNo, int memberNo) {
		String query = "delete from board_comment_like where board_comment_no=? and member_no=?";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertExerciseCommentLike(int boardCommentNo, int memberNo) {
		String query = "insert into board_comment_like values(?,?)";
		Object[] params = {boardCommentNo,memberNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int selectExerciseCommentLikeCount(int boardCommentNo) {
		String query = "select count(*) from board_comment_like where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
	
	//댓글
	public int insertExerciseComment(ExerciseComment ec) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,?,?,to_char(sysdate,'YYYY-MM-DD'))";
		String exerciseCommentRef = ec.getBoardCommentRef() == 0 ? null : String.valueOf(ec.getBoardCommentRef());
		Object[] params = {ec.getBoardCommentContent(),ec.getMemberNo(),ec.getBoardRef(),exerciseCommentRef};
		int result = jdbc.update(query,params);
		return result;
	}
	
	public List selectExerciseCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,"
				+ "to_char(board_comment_date,'yyyy-mm-dd')as board_comment_date,member_no from board_comment join member using (member_no) where board_ref=? and board_comment_ref is null order by 1)bc";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, exerciseCommentRowMapper,params);
		return list;
	}
	public List selectExerciseReCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like from (select board_comment_no,board_comment_content,member_id as board_comment_writer, board_ref,board_comment_ref,"
				+ "to_char(board_comment_date,'yyyy-mm-dd')as board_comment_date,member_no from board_comment join member using (member_no) where board_ref=? and board_comment_ref is not null order by 1)bc";
		Object[] params = {memberNo,boardNo};
		List list = jdbc.query(query, exerciseCommentRowMapper, params);
		return list;
	}

	public int updateComment(ExerciseComment ec) {
		String query = "update board_comment set board_comment_content=? where board_comment_no=?";
		Object[] params = {ec.getBoardCommentContent(),ec.getBoardCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	public int deleteComment(int boardCommentNo) {
		String query = "delete from board_comment where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int result = jdbc.update(query,params);
		return result;
	}

	//삭제
	public List selectExerciseFile(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseFileRowMapper, params);
		return list;
	}

	public int deleteExercise(int boardNo) {
		String query = "delete from board where board_no=?";
		Object[] params = {boardNo};
		int result = jdbc.update(query, params);
		return result;
	}

	//수정
	public Exercise selectOneExercise(int boardNo) {
		String query = "select * from board where board_no=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, exerciseupdateRowMapper, params);
		if(list.isEmpty()) {
			return null;			
		}
		return (Exercise)list.get(0);
	}

	public int updateExercise(Exercise e) {
		String query = "update board set board_title=?,board_content=?,read_count = read_count-1 where board_no=?";
		Object[] params = {e.getBoardTitle(),e.getBoardContent(),e.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertExerciseFile(ExerciseFile exerciseFile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = {exerciseFile.getFilename(),exerciseFile.getFilepath(),exerciseFile.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public ExerciseFile selectOneExerciseFile(int fileNo) {
		String query = "select * from board_file where file_no=?";
		Object[] params = {fileNo};
		List list = jdbc.query(query, exerciseFileRowMapper, params);
		if(list.isEmpty()) {
			return null;			
		}
		return (ExerciseFile)list.get(0);
	}

	public int deleteExerciseFile(int fileNo) {
		String query = "delete from board_file where file_no=?";
		Object[] params = {fileNo};
		int result = jdbc.update(query, params);
		return result;
	}


	public List<Exercise> searchExerciseList(String keyword, int start, int end) {
		String query = "select * from (select rownum rnum, b.* from ("
                + "select board_no, member_no, board_title, board_category, read_count, reg_date, member_id as board_writer, "
                + "(select count(*) from board_comment where board_ref = board.board_no) as comment_count, "
                + "(select count(*) from board_like where board_no = board.board_no) as like_count "
                + "from board join member using(member_no) "
                + "where board_category = 'I1' and board_title like ? order by 1 desc)b)bb "
                + "where rnum between ? and ?";
        Object[] params = {"%" + keyword + "%", start, end};
        return jdbc.query(query, exerciseListRowMapper, params);
    }

    public int searchExerciseTotalCount(String keyword) {
        String query = "select count(*) from board where board_category = 'I1' and board_title like ?";
        return jdbc.queryForObject(query, Integer.class, "%" + keyword + "%");
    }

	

	
	
	
	

	
	
	
}
