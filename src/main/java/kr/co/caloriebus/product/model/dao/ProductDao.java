package kr.co.caloriebus.product.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
		String query="select * from product";
		List list = jdbc.query(query, productRowMapper);
		return list;
	}
	
	public int productInsert(Product p) {
		String query = "insert into product values(product_seq.nextval,?,?,?,?,?,?,?,?,?)";
		Object[] params = {p.getProductTitle(),p.getProductContent(),p.getProductPrice(),p.getProductDcPrice(),p.getProductMinAmount(),p.getProductMaxAmount(),p.getStartDate(),p.getEndDate(),p.getProductImg()};
		int result = jdbc.update(query, params);
		return result;
	}


	public Product selectOneProduct(String productTitle) {
		String query = "select * from product where product_title = ?";
		Object[] params = {productTitle};
		List list = jdbc.query(query, productRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Product)list.get(0);
		}
	}
	
	/*
	public int productFileInsert(ProductFile productList) {
		String query = "insert into product_file values(product_file_seq.nextval,?,?,?)";
		Object[] params = {productList.getFilename(),productList.getFilepath(),productList.getProductNo()};
		int result = jdbc.update(query, params);
		return result;
	}
	*/


}
