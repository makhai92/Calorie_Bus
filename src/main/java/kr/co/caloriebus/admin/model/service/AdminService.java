package kr.co.caloriebus.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.admin.model.dao.AdminDao;
import kr.co.caloriebus.admin.model.dto.MemberListData;
import kr.co.caloriebus.admin.model.dto.PrizeListData;
import kr.co.caloriebus.admin.model.dto.PurchaseListData;
import kr.co.caloriebus.faq.model.dto.FaqListData;
import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.rulletpage.model.dto.RulletPage;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public PurchaseListData getAllFunding(int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage + 1;
		List list = adminDao.getAllFunding(start,end);
		int totalCount = adminDao.selectAllFundingCount();
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
			pageNavi += "<a class = 'page-item' href='/admin/purchaseHistory?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<a class='page-item' href='/admin/purchaseHistory?reqPage="+pageNo+"'>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/admin/purchaseHistory?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admin/purchaseHistory?reqPage="+pageNo+"'>";
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
			pageNavi +=	"<a class='page-item' href='/admin/purchaseHistory?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
			pageNo++;
		}
		pageNavi += "</ul>";
		
		PurchaseListData pld = new PurchaseListData(list,pageNavi);
		return pld;
	}

	public int changeOrderState(Funding f) {
		int result = adminDao.changeOrderState(f);
		return result;
	}

	public MemberListData getAllMember(int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage + 1;
		List list = adminDao.selectAllMember(start,end);
		int totalCount = adminDao.selectAllMemberCount();
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
			pageNavi += "<a class = 'page-item' href='/admin/gradeChange?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<a class='page-item' href='/admin/gradeChange?reqPage="+pageNo+"'>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/admin/gradeChange?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admin/gradeChange?reqPage="+pageNo+"'>";
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
			pageNavi +=	"<a class='page-item' href='/admin/gradeChange?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
			pageNo++;
		}
		pageNavi += "</ul>";
		
		MemberListData mld = new MemberListData(list,pageNavi);
		
		return mld;
	}
	
	
	public int memberLevelChange(Member m) {
		int result = adminDao.memberLevelChange(m);
		return result;
	}

	public PrizeListData getAllDetail(int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage + 1;
		List list = adminDao.selectAllDetail(start,end);
		int totalCount = adminDao.selectAllDetailCount();
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
			pageNavi += "<a class = 'page-item' href='/admin/prizeDetails?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<a class='page-item' href='/admin/prizeDetails?reqPage="+pageNo+"'>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page href='/admin/prizeDetails?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/admin/prizeDetails?reqPage="+pageNo+"'>";
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
			pageNavi +=	"<a class='page-item' href='/admin/prizeDetails?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
			pageNo++;
		}
		pageNavi += "</ul>";
		
		PrizeListData pld = new PrizeListData(list,pageNavi);
		
		return pld;
	}

	public int eventStateUpdate(RulletPage r) {
		int result = adminDao.eventStateUpdate(r);
		return result;
	}

	
}
