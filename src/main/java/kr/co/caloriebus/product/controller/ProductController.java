package kr.co.caloriebus.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductFile;
import kr.co.caloriebus.product.model.service.ProductService;
import kr.co.caloriebus.util.FileUtils;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/list")
	public String list() {
		return "/product/list";
	}
	
	@GetMapping(value="/writerFrm")
	public String writerFrm() {
		return "/product/writerFrm";
	}
	
	@PostMapping(value="/insert")
	public String insert(Product p, MultipartFile[] upfile) {
		int result = productService.productInsert(p);
		if(result>0) {
			//productNo를 조회해와서 productFile에 이미지 추가 등록
			Product product = productService.selectProductNo(p.getProductTitle());
			
			List<ProductFile> fileList = new ArrayList<ProductFile>();
			if (!upfile[0].isEmpty()) {
				// C:/Temp/upload/product/main
				String savepath = root + "/product/main";
				for (MultipartFile file : upfile) {
					// 사용자가 업로드한 파일 이름 출력
					String filename = file.getOriginalFilename(); 
					String filepath = fileUtils.upload(savepath, file);
					ProductFile productfile = new ProductFile();
					productfile.setFilename(filename);
					productfile.setFilepath(filepath);
					productfile.setProductNo(product.getProductNo());
					fileList.add(productfile);
				}
			}
			int result2 = productService.productFileInsert(fileList);
			if(result2>0) {
				
			}
		}
		return "/product/list";
	}
}
