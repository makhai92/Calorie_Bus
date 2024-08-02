package kr.co.caloriebus.product.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.caloriebus.product.model.dao.ProductDao;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
}
