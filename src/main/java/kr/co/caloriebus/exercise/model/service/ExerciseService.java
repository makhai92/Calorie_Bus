package kr.co.caloriebus.exercise.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.exercise.model.dao.ExerciseDao;
import kr.co.caloriebus.exercise.model.dto.Exercise;
import kr.co.caloriebus.exercise.model.dto.ExerciseComment;
import kr.co.caloriebus.exercise.model.dto.ExerciseFile;
import kr.co.caloriebus.exercise.model.dto.ExerciseListData;


@Service
public class ExerciseService {
	@Autowired
	private ExerciseDao exerciseDao;
	
	//번호
	public ExerciseListData selectBoardList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		//요청페이지에 필요한 공지사항 목록을 조회
		List list = exerciseDao.selectBoardList(start,end);
		int totalCount = exerciseDao.selectBoardTotalCount();
		//전체페이지 수 계산
		int totalPage = 0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		//System.out.println("totalPage :"+totalPage);
		//페이지 내비 사이즈 지정
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		//html코드 작성(페이지내비게이션 작성)
		String pageNavi = "<ul class='page-ul'>";
		//'<'이전버튼(1페이지로 시작하지 않으면)
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/exercise/list?&I1reqPage="+(pageNo-1)+"'>";	
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}	
		//페이지 구성
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			//숫자에 디자인표시
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active' href='/exercise/list?&I1reqPage="+pageNo+"'>";				
			}else {
				pageNavi += "<a class='page-item' href='/exercise/list?I1&reqPage="+pageNo+"'>";		
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/exercise/list?&reqPage="+pageNo+"'>";	
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		ExerciseListData eld = new ExerciseListData(list,pageNavi);
	
		return eld;
	}
	
	@Transactional
	public int insertExercise(Exercise e, ArrayList<ExerciseFile> fileList) {
		int result = exerciseDao.insertExercise(e);
		if(result >0) {
			int boardNo = exerciseDao.selectBoardNo();
			for(ExerciseFile exerciseFile : fileList) {
				exerciseFile.setBoardNo(boardNo);
				result += exerciseDao.insertBoardFile(exerciseFile); 
			}
		}
		return result;
	}
	
	@Transactional
	public Exercise selectExercise(int memberNo, int boardNo, String check) {
		Exercise e = exerciseDao.selectExercise(memberNo,boardNo);
		if(e != null) {
			if(check == null) {
				int result = exerciseDao.updateReadCount(boardNo);
			}
			List fileList = exerciseDao.selectExerciseList(boardNo);
			e.setFileList(fileList);			
		}
		return e;
	}
	
	@Transactional
	public int exerciseLikePush(int boardNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike == 1) {
			result = exerciseDao.deleteExerciseLike(boardNo,memberNo);
		}else {
			result = exerciseDao.insertExerciseLike(boardNo,memberNo);
		}
		if(result > 0) {
			int likeCount = exerciseDao.selectExerciseLikeCount(boardNo);
			return likeCount;
		}else {
			return -1;			
		}
	}
	
	@Transactional
	public int exerciseCommentLikePush(int boardCommentNo, int isLike, int memberNo) {
		int result =0;
		if(isLike == 1) {
			result = exerciseDao.deleteExerciseCommentLike(boardCommentNo,memberNo);
		}else {
			result = exerciseDao.insertExerciseCommentLike(boardCommentNo,memberNo);
		}
		if(result > 0) {
			int likeCount = exerciseDao.selectExerciseCommentLikeCount(boardCommentNo);
			return likeCount;
		}else {
			return -1;			
		}
		
	}
	
	@Transactional
	public int insertExerciseComment(ExerciseComment ec) {
		int result = exerciseDao.insertExerciseComment(ec);
		return result;
	}

	public List<ExerciseFile> deleteExercise(int boardNo) {
		List list = exerciseDao.selectExerciseFile(boardNo);
		int result = exerciseDao.deleteExercise(boardNo);
		if(result > 0) {
			return list;
		}
		return null;
	}


	

	
	
	
	
	
	
	
	

}
