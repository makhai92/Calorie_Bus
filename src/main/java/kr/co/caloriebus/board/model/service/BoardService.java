package kr.co.caloriebus.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.board.model.dao.BoardDao;
import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardComment;
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

	@Transactional
	public Board selectBoard(int memberNo,int boardNo, String check) {
		Board b = boardDao.selectBoard(memberNo,boardNo);
		if(b != null) {
			if(check == null) {
				int result = boardDao.updateReadCount(boardNo);
			}
			List fileList = boardDao.selectBoardFileList(boardNo);
			b.setFileList(fileList);
			List boardCommentList = boardDao.selectBoardCommentList(boardNo,memberNo);
			b.setBoardCommentList(boardCommentList);
		}
		return b;
	}

	@Transactional
	public int boardLikePush(int boardNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike==1) {
			result = boardDao.deleteBoardLike(boardNo,memberNo);
		}else {
			result = boardDao.insertBoardLike(boardNo,memberNo);
		}
		if(result >0) {
			int likeCount = boardDao.selectBoardLikeCount(boardNo);
			return likeCount;
		}else {
			return -1;
		}
	}

	@Transactional
	public int insertBoardComment(BoardComment bc) {
		int result = boardDao.insertBoardComment(bc);
		return result;
	}

	@Transactional
	public int boardCommentLikePush(int boardCommentNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike==1) {
			result = boardDao.deleteBoardCommentLike(boardCommentNo,memberNo);
		}else {
			result = boardDao.insertBoardCommentLike(boardCommentNo,memberNo);
		}
		if(result >0) {
			int likeCount = boardDao.selectBoardCommentLikeCount(boardCommentNo);
			return likeCount;
		}else {
			return -1;
		}
	}

	public BoardListData searchBoardList(String category, String type, String keyword, int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end-numPerPage+1;
		List list = null;
		int totalCount = 0;
		if(category.equals("all")) {
			if(type.equals("title")) {
				list = boardDao.selectBoardTitleList(start,end,keyword);
				totalCount = boardDao.selectBoardTitleTotalCount(keyword);
			}else if(type.equals("writer")) {
				list = boardDao.selectBoardWriterList(start,end,keyword);
				totalCount = boardDao.selectBoardWriterTotalCount(keyword);
			}
		}else {
			if(type.equals("title")) {
				list = boardDao.selectBoardTitleList(category,start,end,keyword);
				totalCount = boardDao.selectBoardTotalTitleCount(category,keyword);
			}else if(type.equals("writer")) {
				list = boardDao.selectBoardWriterList(category,start,end,keyword);
				totalCount = boardDao.selectBoardTotalWriterCount(category,keyword);
			}
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
			pageNavi += "<a class='page-item' href='/board/search?category="+category+"&reqPage="+(pageNo-1)+"&type="+type+"&keyword="+keyword+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active' href='/board/search?category="+category+"&reqPage="+pageNo+"&type="+type+"&keyword="+keyword+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/board/search?category="+category+"&reqPage="+pageNo+"&type="+type+"&keyword="+keyword+"'>";
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
			pageNavi += "<a class='page-item' href='/board/search?category="+category+"&reqPage="+pageNo+"&type="+type+"&keyword="+keyword+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		BoardListData bld = new BoardListData(list, pageNavi);
		return bld;
	}
	public List boardReCommentList(int boardCommentNo,int memberNo) {
		List list = boardDao.selectBoardReCommentList(boardCommentNo, memberNo);
		return list;
	}

	@Transactional
	public int updateComment(BoardComment bc) {
		int result = boardDao.updateComment(bc);
		return result;
	}

	@Transactional
	public int deleteComment(int boardCommentNo) {
		int result = boardDao.deleteComment(boardCommentNo);
		return result;
	}

	@Transactional
	public List<BoardFile> deleteBoard(int boardNo, int memberNo) {
		List list = boardDao.selectBoardFileList(boardNo);
		int result = boardDao.deleteBoard(boardNo,memberNo);
		if(result > 0) {
			return list;
		}
		return null;
	}

	public Board selectOneBoard(int boardNo) {
		Board board = boardDao.selectOneBoard(boardNo);
		List list = boardDao.selectBoardFileList(boardNo);
		board.setFileList(list);
		return board;
	}
	
	// 마이페이지 용 내 게시글 보기
	public BoardListData selectMyBoardList(int memberNo, int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = boardDao.selectMyBoardList(memberNo, start, end);
		int totalCount = boardDao.selectMyBoardTotalCount(memberNo);
		
		int totalPage = 0;
		if(totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;
		}
		else {
			totalPage = totalCount / numPerPage + 1;
		}
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1) / pageNaviSize) * pageNaviSize + 1;
		String pageNavi = "<ul class='pageNavi-ul'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='pageNavi-li' href='/board/myboard?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='pageNavi-li active' href='/board/myboard?reqPage=" + pageNo + "'>";
			}
			else {
				pageNavi += "<a class='pageNavi-li' href='/board/myboard?reqPage=" + pageNo + "'>";
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
			pageNavi += "<a class='pageNavi-li' href='/board/myboard?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		BoardListData bld = new BoardListData(list, pageNavi);
		return bld;
	}

	@Transactional
	public List<BoardFile> updateBoard(Board b, ArrayList<BoardFile> fileList,int[] delFileNo) {
		List<BoardFile> delFileList = new ArrayList<BoardFile>();
		int result = boardDao.updateBoard(b);
		if(result > 0) {
			for(BoardFile file : fileList) {
				file.setBoardNo(b.getBoardNo());
				result += boardDao.insertBoardFile(file);
			}
			if(delFileNo != null) {
				for(int fileNo : delFileNo) {
					BoardFile boardFile = boardDao.selectOneBoardFile(fileNo);
					result += boardDao.deleteBoardFile(fileNo);
					delFileList.add(boardFile);
				}
			}
		}
		int resultTotal = delFileNo == null?fileList.size()+1:fileList.size()+1+delFileNo.length;
		if(resultTotal == result) {
			return delFileList;
		}else {
			return null;
		}
		
	}

	

}
