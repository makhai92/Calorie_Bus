package kr.co.caloriebus.inquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.inquery.dao.InqueryDao;
import kr.co.caloriebus.inquery.dto.InqueryListData;

@Service
public class InqueryService {
	@Autowired
	private InqueryDao inqueryDao;

	public InqueryListData selectInqueryList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;	
		List list = inqueryDao.selectInqueryList(start, end);
		
		int totalCount = inqueryDao.selectInqueryTotalCount();
		
		int totalPage = 0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		
		int pageNaviSize=5;
		
		int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize +1;
		
		String pageNavi = "<ul>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi +=	"<a class='page-item' href='/inquery/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}	
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi +=	"<a class='page-item' href='/inquery/list?reqPage="+pageNo+"'>";	
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/inquery/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/inquery/list?reqPage="+pageNo+"'>";
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
				pageNavi +=	"<a class='page-item' href='/inquery/list?reqPage="+pageNo+"'>";
				pageNavi += "<span class='material-icons'>chevron_right</span>";
				pageNavi += "</a></li>";
				pageNo++;
			}
		pageNavi +="</ul>";
		
		InqueryListData ild = new InqueryListData(list, pageNavi);
		
		System.out.println(pageNavi);
		
		return ild;
	}
}
