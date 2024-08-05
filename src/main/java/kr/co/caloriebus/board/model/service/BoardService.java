package kr.co.caloriebus.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.board.model.dao.BoardDao;
import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.board.model.dto.BoardListData;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public BoardListData selectBoardList(String category, int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end-numPerPage+1;
		List list = null;
		int totalCount = 0;
		if(category.equals("all")) {
			list = boardDao.selectBoardList(start,end);
			totalCount = boardDao.selectBoardTotalCount();
		}else {
			list = boardDao.selectBoardList(category,start,end);
			totalCount = boardDao.selectBoardTotalCount(category);
		}
		int totalPage = 0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi = "<ul class='page-ul'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/board/list?category="+category+"&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active' href='/board/list?category="+category+"&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/board/list?category="+category+"&reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/board/list?category="+category+"&reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		BoardListData bld = new BoardListData(list, pageNavi);
		return bld;
	}

	@Transactional
	public int insertBoard(Board b, ArrayList<BoardFile> fileList) {
		int result = boardDao.insertBoard(b);
		if(result > 0) {
			int boardNo = boardDao.selectBoardNo();
			for (BoardFile boardFile : fileList) {
				boardFile.setBoardNo(boardNo);
				result += boardDao.insertBoardFile(boardFile);
			}
		}
		return result;
	}
}
