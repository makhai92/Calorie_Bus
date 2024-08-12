package kr.co.caloriebus.inquery.service;

import java.util.ArrayList;
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

	    List<Inquery> list = inqueryDao.selectInqueryList(start, end);

	    for (Inquery inquery : list) {
	        List<Integer> replies = inqueryDao.selectReplyCount(inquery.getInqueryNo());
	        inquery.setHasReply(!replies.isEmpty()); // 답변 여부를 설정
	    }

	    int totalCount = inqueryDao.selectInqueryTotalCount();
	    int totalPage = (totalCount + numPerPage - 1) / numPerPage; // 페이지 수 계산

	    int pageNaviSize = 5;
	    int pageNo = (reqPage - 1) / pageNaviSize * pageNaviSize + 1;

	    StringBuilder pageNavi = new StringBuilder("<ul class='pagination circle-style'>");
	    if (pageNo != 1) {
	        pageNavi.append("<li><a class='page-item' href='/inquery/inqueryMain?reqPage=").append(pageNo - 1).append("'>")
	                .append("<span class='material-icons'>chevron_left</span></a></li>");
	    }
	    for (int i = 0; i < pageNaviSize; i++) {
	        if (pageNo > totalPage) break;
	        pageNavi.append("<li><a class='page-item")
	                .append(pageNo == reqPage ? " active-page" : "")
	                .append("' href='/inquery/inqueryMain?reqPage=").append(pageNo).append("'>")
	                .append(pageNo).append("</a></li>");
	        pageNo++;
	    }
	    if (pageNo <= totalPage) {
	        pageNavi.append("<li><a class='page-item' href='/inquery/inqueryMain?reqPage=").append(pageNo).append("'>")
	                .append("<span class='material-icons'>chevron_right</span></a></li>");
	    }
	    pageNavi.append("</ul>");

	    return new InqueryListData(list, pageNavi.toString());
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
	
	public int insertReply(InqueryReply ir) {
		int result = inqueryDao.insertReply(ir);
		return result;
	}
	public int deleteReply(InqueryReply ir) {
		int result = inqueryDao.deleteReply(ir);
		return result;
	}
	public List<InqueryFile> deleteInquery(int inqueryNo) {
		List list = inqueryDao.selectInqueryFile(inqueryNo);
		int result = inqueryDao.deleteInquery(inqueryNo);
		if(result > 0) {
			return list;
		}
		return null;
	}
	public int updateReply(InqueryReply ir) {
		int result = inqueryDao.updateReply(ir);
		return result;
	}
	public List<InqueryFile> inqueryUpdate(Inquery i, List<InqueryFile> fileList, int[] delFileNo) {
		List <InqueryFile> delFileList = new ArrayList <InqueryFile> ();
		int result = inqueryDao.inqueryUpdate(i);
		if(result > 0) {
			for (InqueryFile inqueryFile : fileList) {
				result += inqueryDao.insertInqueryFile(inqueryFile);
			}
			if(delFileNo != null) {
				for(int fileNo : delFileNo) {
					System.out.println("fileNo : "+fileNo);
					InqueryFile inqueryFile = inqueryDao.selectOneInqueryFile(fileNo);
					delFileList.add(inqueryFile);
					result += inqueryDao.deleteInqueryFile(fileNo);
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
	public Inquery getOneInquery(int inqueryNo) {
		Inquery i = inqueryDao.selectOneInquery(inqueryNo);
		List list = inqueryDao.selectInqueryFile(inqueryNo);
		i.setFileList(list);
		return i;
	}
	
	
}
