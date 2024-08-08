package kr.co.caloriebus.product.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductFile;
import kr.co.caloriebus.product.model.dto.ProductFileRowMapper;
import kr.co.caloriebus.product.model.dto.ProductRowMapper;

@Repository
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ProductRowMapper productRowMapper;
	@Autowired
	private ProductFileRowMapper productFileRowMapper;
	
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
	


}
