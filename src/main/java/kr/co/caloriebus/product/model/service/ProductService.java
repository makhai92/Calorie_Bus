package kr.co.caloriebus.product.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.inquery.dto.InqueryListData;
import kr.co.caloriebus.product.model.dao.ProductDao;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductFile;
import kr.co.caloriebus.product.model.dto.ProductReview;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	public List selectAllProduct() {
		List list = productDao.selectAllProduct();
		return list;
	}
	
	@Transactional
	public int productInsert(Product p) {
		int result = productDao.productInsert(p);
		return result;
	}
	
	public Product selectOneProduct(int productNo) {
		Product p = productDao.selectOneProduct(productNo);
		return p;
	}

	@Transactional
	public int productFileInsert(List<ProductFile> fileList) {
		int result = 0;
		for(ProductFile productList : fileList) {
			result += productDao.productFileInsert(productList);			
		}
		return result;
	}
	
	@Transactional
	public int deleteProduct(int productNo) {
		int result = productDao.deleteProduct(productNo);
		return result;
	}
	
	@Transactional
	public int update1Product(Product p) {
		int result = productDao.update1Product(p);
		return result;
	}
	
	@Transactional
	public int update2Product(Product p) {
		int result = productDao.update2Product(p);
		return result;
	}
	
	@Transactional
	public int insertFunding(Funding f) {
		int result = productDao.insertFunding(f);
		return result;
	}

	public List selectAllProductReview(int productNo) {
		List list = productDao.selectAllProductReview(productNo);
		return list;
	}

	public int selectFundingNo(int productNo) {
		int fundingNo = productDao.selectFundingNo(productNo);
		return fundingNo;
	}
	
	@Transactional
	public int reviewInsert(ProductReview pr) {
		int result = productDao.reviewInsert(pr);
		return result;
	}

	// 마이페이지 용 내 공구 내역 보기
	public InqueryListData selectMyFundingList(int memberNo, int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = productDao.selectMyFundingList(memberNo, start, end);
		int totalCount = productDao.selectMyFundingTotalCount(memberNo);
		
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
			pageNavi += "<a class='pageNavi-li' href='/funding/myfunding?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='pageNavi-li active' href='/funding/myfunding?reqPage=" + pageNo + "'>";
			}
			else {
				pageNavi += "<a class='pageNavi-li' href='/funding/myfunding?reqPage=" + pageNo + "'>";
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
			pageNavi += "<a class='pageNavi-li' href='/funding/myfunding?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		InqueryListData ild = new InqueryListData(list, pageNavi);
		return ild;
	}
	

}
