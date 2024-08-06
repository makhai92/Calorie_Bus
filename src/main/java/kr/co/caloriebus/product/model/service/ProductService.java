package kr.co.caloriebus.product.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.caloriebus.product.model.dao.ProductDao;
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

	public Product selectOneProduct(String productTitle) {
		Product product = productDao.selectOneProduct(productTitle);
		return product;
	}

	@Transactional
	public int productFileInsert(List<ProductFile> fileList) {
		int result = 0;
		for(ProductFile productList : fileList) {
			result += productDao.productFileInsert(productList);			
		}
		return result;
	}


}
