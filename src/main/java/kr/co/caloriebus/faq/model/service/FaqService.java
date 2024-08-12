package kr.co.caloriebus.faq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.faq.model.dao.FaqDao;
import kr.co.caloriebus.faq.model.dto.Faq;
import kr.co.caloriebus.faq.model.dto.FaqListData;
import kr.co.caloriebus.faq.model.dto.FaqSearch;

@Service
public class FaqService {
	@Autowired
	private FaqDao faqDao;
	
	@Transactional
	public int insertFaq(Faq f) {
		int result = faqDao.insertFaq(f);
		return result;
	}

	public FaqListData selectAllFaq(int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage + 1;
		List list = faqDao.selectAllFaq(start,end);
		int totalCount = faqDao.selectAllFaqCount();
		int totalPage = 0;
		if(totalCount%numPerPage==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		
		int pageNaviSize = 5;
		
		int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize + 1;
		
		String pageNavi= "<ul class='pagination circle-style'>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class = 'page-item' href='/faq/faqMain?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<a class='page-item' href='/faq/faqMain?reqPage="+pageNo+"'>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/faq/faqMain?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/faq/faqMain?reqPage="+pageNo+"'>";
			}
			pageNavi += "<li>";
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) {
			break;
			}
		}
		//다음버튼(최종페이지를 출력하지 않으면)
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi +=	"<a class='page-item' href='/faq/faqMain?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
			pageNo++;
		}
		pageNavi += "</ul>";
		
		FaqListData fld = new FaqListData(list,pageNavi);
		
		
		return fld;
	}

	public Faq selectOneFaq(int faqNo) {
		Faq f = faqDao.selectOneFaq(faqNo);
		return f;
	}

	public int deleteFaq(int faqNo) {
		int result = faqDao.deleteFaq(faqNo);
		if(result>0) {
			return faqNo;
		}
		return result;
	}

	public Faq getOneFaq(int faqNo) {
		Faq f = faqDao.selectOneFaq(faqNo);
		return f;
	}

	public int updateFaq(Faq f) {
		int result = faqDao.updateFaq(f);
		return result;
	}

	public FaqListData searchAllFaq(FaqSearch fs,int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = null;
		if(fs.getSearchField().equals("sTitle")) {
			list = faqDao.searchFaqTitle(fs.getKeyword(),start, end);
		}else {
			list = faqDao.searchFaqContent(fs.getKeyword(),start, end);
		}
		int totalCount = faqDao.selectAllFaqCount();
		int totalPage =0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		int pageNaviSize = 5;
		int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize + 1;
		
		String pageNavi= "<ul class='pagination circle-style'>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class = 'page-item' href='/faq/faqMain?searchField="+fs.getSearchField()+"&keyword="+fs.getKeyword()+"&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<a class='page-item' href='faq/faqSearch?searchField="+fs.getSearchField()+"&keyword="+fs.getKeyword()+"&reqPage="+pageNo+"'>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/faq/faqSearch?searchField="+fs.getSearchField()+"&keyword="+fs.getKeyword()+"&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/faq/faqSearch?searchField="+fs.getSearchField()+"&keyword="+fs.getKeyword()+"&reqPage="+pageNo+"'>";
			}
			pageNavi += "<li>";
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) {
			break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi +=	"<a class='page-item' href='/faq/faqSearch?searchField="+fs.getSearchField()+"&keyword="+fs.getKeyword()+"&reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
			pageNo++;
		}
		pageNavi += "</ul>";
		
		FaqListData fld = new FaqListData(list,pageNavi);
		
		return fld;
	
	}
	
	



}
