package kr.co.caloriebus.inquery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.inquery.dao.InqueryDao;
import kr.co.caloriebus.inquery.dto.Inquery;
import kr.co.caloriebus.inquery.dto.InqueryFile;
import kr.co.caloriebus.inquery.dto.InqueryListData;
import kr.co.caloriebus.inquery.dto.InqueryReply;

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
			pageNavi +=	"<a class='page-item' href='/inquery/inqueryMain?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}	
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi +=	"<a class='page-item' href='/inquery/inqueryMain?reqPage="+pageNo+"'>";	
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/inquery/inqueryMain?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/inquery/inqueryMain?reqPage="+pageNo+"'>";
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
				pageNavi +=	"<a class='page-item' href='/inquery/inqueryMain?reqPage="+pageNo+"'>";
				pageNavi += "<span class='material-icons'>chevron_right</span>";
				pageNavi += "</a></li>";
				pageNo++;
			}
		pageNavi +="</ul>";
		
		InqueryListData ild = new InqueryListData(list, pageNavi);
		
		System.out.println(pageNavi);
		
		return ild;
	}
	@Transactional
	public int insertInquery(Inquery i, List<InqueryFile> fileList) {
		int result = inqueryDao.insertInquery(i);
		if(result > 0) {
			int inqueryNo = inqueryDao.selectInqueryNo();
			
			for(InqueryFile inqueryFile : fileList) {
				inqueryFile.setInqueryNo(inqueryNo);
				result += inqueryDao.insertInqueryFile(inqueryFile);
			}
		}
		return result;
	}
	/*
	@Transactional
	public Inquery selectOneInquery(int inqueryNo, int memberNo) {
		Inquery i = inqueryDao.selectOneInquery(inqueryNo);
		
		List fileList = inqueryDao.selectInqueryFile(inqueryNo);
		i.setFileList(fileList);
		
		List<Reply> ReplyList = inqueryDao.selectReplyList(inqueryNo, memberNo);
		
		return null;
	}
	*/
	public Inquery selectOneInquery(int inqueryNo, String check, int memberNo) {
		Inquery i = inqueryDao.selectOneInquery(inqueryNo);
		List fileList = inqueryDao.selectInqueryFile(inqueryNo);
		i.setFileList(fileList);
		List<InqueryReply> ReplyList = inqueryDao.selectReplyList(inqueryNo, memberNo);
		i.setReplyList(ReplyList);
		return i;
	}
	
	
	
	// 마이페이지 용 내 게시글 보기
	public InqueryListData selectMyInqueryList(int memberNo, int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = inqueryDao.selectMyInqueryList(memberNo, start, end);
		int totalCount = inqueryDao.selectMyInqueryTotalCount(memberNo);
		
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
			pageNavi += "<a class='pageNavi-li' href='/inquery/myinquery?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='pageNavi-li active' href='/inquery/myinquery?reqPage=" + pageNo + "'>";
			}
			else {
				pageNavi += "<a class='pageNavi-li' href='/inquery/myinquery?reqPage=" + pageNo + "'>";
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
			pageNavi += "<a class='pageNavi-li' href='/inquery/myinquery?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		InqueryListData ild = new InqueryListData(list, pageNavi);
		return ild;
	}
	
}
