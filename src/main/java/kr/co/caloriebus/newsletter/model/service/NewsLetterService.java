package kr.co.caloriebus.newsletter.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.board.model.dto.BoardComment;
import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterComment;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFile;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterListData;
import kr.co.caloriebus.newsletter.model.dao.NewsLetterDao;

@Service
public class NewsLetterService {
	@Autowired
	private NewsLetterDao newsletterDao;

	public NewsLetterListData selectNewsLetterList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end-numPerPage+1;
		int totalCount = 0;
		List list = newsletterDao.selectNewsLetterList(start,end);
		totalCount = newsletterDao.selectNewsLetterTotalCount();
		int rNum = totalCount-(reqPage-1)*10;
		for(int i=0;i<list.size();i++) {
			NewsLetter nl = (NewsLetter)list.get(i);
			nl.setRNum(rNum);
			rNum--;
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
			pageNavi += "<a class='page-item' href='/newsletter/list?&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active' href='/newsletter/list?&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/newsletter/list?&reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/newsletter/list?&reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		NewsLetterListData nlld = new NewsLetterListData(list, pageNavi);
		return nlld;
	}

	public int insertNewsLetter(NewsLetter nl, ArrayList<NewsLetterFile> fileList) {
		int result = newsletterDao.insertNewsLetter(nl);
		if(result > 0) {
			int boardNo = newsletterDao.selectBoardNo();
			for (NewsLetterFile newsletterFile : fileList) {
				newsletterFile.setBoardNo(boardNo);
				result += newsletterDao.insertNewsLetterFile(newsletterFile);
			}
		}
		return result;
	}
	@Transactional
	public NewsLetter selectNewsLetter(int memberNo,int boardNo, String check) {
		NewsLetter n = newsletterDao.selectNewsLetter(memberNo,boardNo);
		if(n != null) {
			if(check == null) {
				int result = newsletterDao.updateReadCount(boardNo);
			}
			List fileList = newsletterDao.selectNewsLetterFileList(boardNo);
			n.setFileList(fileList);
			List commentList = newsletterDao.selectNewsLetterCommentList(boardNo, memberNo);
			n.setBoardCommentList(commentList);
			List reCommentList = newsletterDao.selectNewsLetterReCommentList(boardNo, memberNo);
			n.setBoardReCommentList(reCommentList);
			int commentCount = commentList.size()+reCommentList.size();
			n.setCommentCount(commentCount);
		}
		return n;
	}

	@Transactional
	public int newsletterLikePush(int boardNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike==1) {
			result = newsletterDao.deleteNewsLetterLike(boardNo,memberNo);
		}else {
			result = newsletterDao.insertNewsLetterLike(boardNo,memberNo);
		}
		if(result >0) {
			int likeCount = newsletterDao.selectNewsLetterLikeCount(boardNo);
			return likeCount;
		}else {
			return -1;
		}
	}
	@Transactional
	public int insertNewsLetterComment(NewsLetterComment nlc) {
		int result = newsletterDao.insertNewsLetterComment(nlc);
		return result;
	}

	@Transactional
	public int newsletterCommentLikePush(int boardCommentNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike==1) {
			result = newsletterDao.deleteNewsLetterCommentLike(boardCommentNo,memberNo);
		}else {
			result = newsletterDao.insertNewsLetterCommentLike(boardCommentNo,memberNo);
		}
		if(result >0) {
			int likeCount = newsletterDao.selectNewsLetterCommentLikeCount(boardCommentNo);
			return likeCount;
		}else {
			return -1;
		}
	}
	 @Transactional
	    public int updateNewsLetter(NewsLetter nl, ArrayList<NewsLetterFile> fileList) {
	        int result = newsletterDao.updateNewsLetter(nl);
	        if (result > 0) {
	            newsletterDao.deleteNewsLetterFiles(nl.getBoardNo());
	            for (NewsLetterFile newsletterFile : fileList) {
	                newsletterFile.setBoardNo(nl.getBoardNo());
	                result += newsletterDao.insertNewsLetterFile(newsletterFile);
	            }
	        }
	        return result;
	    }
	 	@Transactional
		public int updateComment(NewsLetterComment nlc) {
			int result = newsletterDao.updateComment(nlc);
			return result;
		}
	 	
		@Transactional
		public int deleteComment(int boardCommentNo) {
			int result = newsletterDao.deleteComment(boardCommentNo);
			return result;
		}
	    @Transactional
	    public int deleteNewsLetter(int boardNo) {
	        newsletterDao.deleteNewsLetterFiles(boardNo);
	        return newsletterDao.deleteNewsLetter(boardNo);
	    }

		public NewsLetterListData searchNewsLetterList(String keyword, int reqPage) {
			int numPerPage = 10;
	        int end = reqPage * numPerPage;
	        int start = end - numPerPage + 1;

	        List<NewsLetter> list = newsletterDao.searchNewsLetterList(keyword, start, end);
	        int totalCount = newsletterDao.searchNewsLetterTotalCount(keyword);

	        int rNum = totalCount - (reqPage - 1) * 10;
	        for (NewsLetter nl : list) {
	            nl.setRNum(rNum--);
	        }

	        int totalPage = (totalCount % numPerPage == 0) ? totalCount / numPerPage : totalCount / numPerPage + 1;
	        int pageNaviSize = 5;
	        int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

	        StringBuilder pageNavi = new StringBuilder("<ul class='page-ul'>");
	        if (pageNo != 1) {
	            pageNavi.append("<li><a class='page-item' href='/newsletter/search?keyword=").append(keyword).append("&reqPage=").append(pageNo - 1).append("'><span class='material-icons'>chevron_left</span></a></li>");
	        }
	        for (int i = 0; i < pageNaviSize && pageNo <= totalPage; i++) {
	            if (pageNo == reqPage) {
	                pageNavi.append("<li><a class='page-item active' href='/newsletter/search?keyword=").append(keyword).append("&reqPage=").append(pageNo).append("'>").append(pageNo).append("</a></li>");
	            } else {
	                pageNavi.append("<li><a class='page-item' href='/newsletter/search?keyword=").append(keyword).append("&reqPage=").append(pageNo).append("'>").append(pageNo).append("</a></li>");
	            }
	            pageNo++;
	        }
	        if (pageNo <= totalPage) {
	            pageNavi.append("<li><a class='page-item' href='/newsletter/search?keyword=").append(keyword).append("&reqPage=").append(pageNo).append("'><span class='material-icons'>chevron_right</span></a></li>");
	        }
	        pageNavi.append("</ul>");

	        return new NewsLetterListData(list, pageNavi.toString());
	    }

		
	}

