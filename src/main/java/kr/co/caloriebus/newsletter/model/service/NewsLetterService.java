package kr.co.caloriebus.newsletter.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFile;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterListData;
import kr.co.caloriebus.newsletter.model.dao.NewsLetterDao;

@Service
public class NewsLetterService {
	@Autowired
	private NewsLetterDao newsletterDao;

	public NewsLetterListData selectNewsLetterList(String category, int reqPage) {
		System.out.println(1);
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end-numPerPage+1;
		int totalCount = 0;
		List list = newsletterDao.selectNewsLetterList(category,start,end);
		totalCount = newsletterDao.selectNewsLetterTotalCount(category);
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
			pageNavi += "<a class='page-item' href='/newsletter/list?category="+category+"&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active' href='/newsletter/list?category="+category+"&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/newsletter/list?category="+category+"&reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/newsletter/list?category="+category+"&reqPage="+pageNo+"'>";
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
}
