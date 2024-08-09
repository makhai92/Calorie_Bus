package kr.co.caloriebus.product.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.admin.model.dto.PurchaseRowMapper;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.product.model.dto.Myfunding;
import kr.co.caloriebus.product.model.dto.MyfundingListRowMapper;
import kr.co.caloriebus.product.model.dto.MyfundingRowMapper;
import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductFile;
import kr.co.caloriebus.product.model.dto.MylikeRowMapper;
import kr.co.caloriebus.product.model.dto.ProductReview;
import kr.co.caloriebus.product.model.dto.ProductReviewRowMapper;
import kr.co.caloriebus.product.model.dto.ProductRowMapper;

@Repository
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ProductRowMapper productRowMapper;
	@Autowired
	private ProductReviewRowMapper productReviewRowMapper;
	@Autowired
	private PurchaseRowMapper purchaseRowMapper;
	@Autowired
	private MyfundingListRowMapper myfundingListRowMapper;
	@Autowired
	private MyfundingRowMapper myfundingRowMapper;
	@Autowired
	private MylikeRowMapper mylikeRowMapper;
	
	public List selectAllProduct() {
		String query="select * from product order by 1 desc";
		List list = jdbc.query(query, productRowMapper);
		return list;
	}
	
	public int productInsert(Product p) {
		String query = "insert into product values(product_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {p.getProductTitle(),p.getProductContent(),p.getProductPrice(),p.getProductDcPrice(),p.getProductMinAmount(),p.getProductMaxAmount(),p.getStartDate(),p.getEndDate(),p.getProductImg(),p.getProductInfo()};
		int result = jdbc.update(query, params);
		return result;
	}


	public Product selectOneProduct(int productNo) {
		String query = "select * from product where product_no = ?";
		Object[] params = {productNo};
		List list = jdbc.query(query, productRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Product)list.get(0);
		}
	}
	
	public int productFileInsert(ProductFile productList) {
		String query = "insert into product_file values(product_file_seq.nextval,?,?,?)";
		Object[] params = {productList.getFilename(),productList.getFilepath(),productList.getProductNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteProduct(int productNo) {
		String query = "delete from product where product_no = ?";
		Object[] params = {productNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int update1Product(Product p) {
		String query = "update product set product_title=?,product_content=?,product_price=?,product_dc_price=?,product_min_amount=?,product_max_amount=?,start_date=?,end_date=?,product_info=? where product_no=?";
		Object[] params = {p.getProductTitle(),p.getProductContent(),p.getProductPrice(),p.getProductDcPrice(),p.getProductMinAmount(),p.getProductMaxAmount(),p.getStartDate(),p.getEndDate(),p.getProductInfo(),p.getProductNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int update2Product(Product p) {
		String query = "update product set product_title=?,product_content=?,product_price=?,product_dc_price=?,product_min_amount=?,product_max_amount=?,start_date=?,end_date=?,product_img=?,product_info=? where product_no=?";
		Object[] params = {p.getProductTitle(),p.getProductContent(),p.getProductPrice(),p.getProductDcPrice(),p.getProductMinAmount(),p.getProductMaxAmount(),p.getStartDate(),p.getEndDate(),p.getProductImg(),p.getProductInfo(),p.getProductNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertFunding(Funding f) {
		String query = "insert into funding values(FUNDING_SEQ.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),1,?,?,?,?,?)";
		Object[] params = {f.getMemberNo(),f.getProductNo(),f.getOrderAmount(),f.getFundingName(),f.getFundingPhone(),f.getFundingAddr(),f.getFundingPostcode()};
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectAllProductReview(int productNo) {
		String query = "select * from product_review where product_no=? order by 1 desc";
		Object[] params = {productNo};
		List list = jdbc.query(query, productReviewRowMapper, params);
		return list;
	}

	public int reviewInsert(ProductReview pr) {
		String query = "insert into product_review values(?,?,?,?,?)";
		Object[] params = {pr.getFundingNo(),pr.getReviewContent(),pr.getReviewImg(),pr.getMemberId(),pr.getProductNo()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	// 마이페이지 용
	public List selectMyfundingList(int memberNo, int start, int end) {
		String query = "select * from (select rownum rnum, m.*, review_content from (select funding_no, member_no, f.product_no, product_title, product_dc_price, product_img, order_date, order_state, order_amount from (funding)f left join (product)p on (f.product_no = p.product_no) where member_no = ? order by 1 desc)m left join (product_review)r on (m.funding_no = r.funding_no)) where rnum between ? and ?";
		Object[] params = {memberNo, start, end};
		List list = jdbc.query(query, myfundingListRowMapper, params);
		return list;
	}
	
	public int selectMyfundingTotalCount(int memberNo) {
		String query = "select count(*) from funding where member_no = ?";
		Object[] params = {memberNo};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	public ProductReview selecOneProductReview(int fundingNo) {
		String query = "select * from product_review where funding_no=?";
		Object[] params = {fundingNo};
		List list = jdbc.query(query, productReviewRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (ProductReview)list.get(0);
		}
	}

	public int reviewUpdate(ProductReview pr) {
		String query = "update product_review set review_content=?,review_img=? where funding_no=?";
		Object[] params = {pr.getReviewContent(),pr.getReviewImg(),pr.getFundingNo()};
		int result = jdbc.update(query, params);
		return result;
	}
	public Myfunding selectMyfunding(int fundingNo) {
		String query = "select funding_no, member_no, f.product_no, product_title, product_dc_price, order_date, order_state, order_amount, funding_name, funding_phone, funding_addr, funding_postcode from (funding)f join (product)p on (f.product_no = p.product_no) where funding_no = ?";
		Object[] params = {fundingNo};
		List list = jdbc.query(query, myfundingRowMapper, params);
		if (list.isEmpty()) {			
			return null;
		} else {
			return (Myfunding)list.get(0);
		}
	}

	public List selectMylike(int memberNo, int start, int end) {
		String query = "select product_no, product_dc_price, product_price, product_title, product_img, end_date from product where product_no in (select product_no from product_like where member_no = ?)";
		Object[] params = {memberNo};
		List list = jdbc.query(query, mylikeRowMapper, params);
		return list;
	}

	public int selectMylikeTotalCount(int memberNo) {
		String query = "select count(*) from product_like where member_no = ?";
		Object[] params = {memberNo};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

}
