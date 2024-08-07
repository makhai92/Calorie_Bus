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
		int numPerPage =10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = exerciseDao.selectBoardList(start, end);
		int totalCount = exerciseDao.selectBoardTotalCount();
		int totalPage = 0;
		if(totalCount%numPerPage ==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		System.out.println("totalPage :"+totalPage);
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/exercise/list?reqPage="+(pageNo-1)+"'>";	
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			//숫자에 디자인표시
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/exercise/list?reqPage="+pageNo+"'>";				
			}else {
				pageNavi += "<a class='page-item' href='/exercise/list?reqPage="+pageNo+"'>";		
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
			pageNavi += "<a class='page-item' href='/exercise/list?reqPage="+pageNo+"'>";	
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		ExerciseListData eld = new ExerciseListData(list,pageNavi);
	
		return eld;
	}
	
	@Transactional
	public int insertBoard(Exercise e, ArrayList<ExerciseFile> fileList) {
		int result = exerciseDao.insertBoard(e);
		if(result > 0) {
			int boardNo = exerciseDao.selectBoardNo();
			for (ExerciseFile exerciseFile : fileList) {
				exerciseFile.setBoardNo(boardNo);
				result += exerciseDao.insertBoardFile(exerciseFile);
			}
		}
		return result;
	}
	
	@Transactional
	public Exercise selectOneBoard(int boardNo) {
		Exercise e = exerciseDao.selectOneBoard(boardNo);
		if(e != null) {
			int result = exerciseDao.updateReadCount(boardNo);
			List fileList = exerciseDao.selectBoardFile(boardNo);
			e.setFileList(fileList);
		}
		return e;
	}

	public List<ExerciseFile> deleteBoard(int boardNo) {
		List list = exerciseDao.selectBoardFile(boardNo);
		int result = exerciseDao.deleteBoard(boardNo);
		if(result > 0) {
			return list;
		}
		return null;
	}

	public Exercise getOneBoard(int boardNo) {
		Exercise e = exerciseDao.selectOneBoard(boardNo);
		List list = exerciseDao.selectBoardFile(boardNo);
		e.setFileList(list);
		return e;
	}

	public List<ExerciseFile> updateBoard(Exercise e, List<ExerciseFile> fileList, int[] delFileNo) {
		List<ExerciseFile> delFileList = new ArrayList<ExerciseFile>();
		int result = exerciseDao.updateBoard(e);
		if(result > 0) {
			for(ExerciseFile exerciseFile : fileList) {
				exerciseFile.setBoardNo(e.getBoardNo());
				result += exerciseDao.insertBoardFile(exerciseFile);
			}
			if(delFileNo != null) {
				for(int fileNo : delFileNo) {
					ExerciseFile exerciseFile = exerciseDao.selectOneBoardfile(fileNo);
					delFileList.add(exerciseFile);
					result += exerciseDao.deleteBoardFile(fileNo);
							
				}
			}
		}
		int updateTotal = delFileNo == null?fileList.size()+1 : fileList.size()+1+delFileNo.length;
		if(updateTotal == result) {
			return delFileList;
		}else {
			return null;			
		}
		
	}
	
	

}
