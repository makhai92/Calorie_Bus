package kr.co.caloriebus.product.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.product.model.dao.ProductDao;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductFile;

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



}
