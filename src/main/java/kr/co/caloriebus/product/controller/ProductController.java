package kr.co.caloriebus.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.product.model.dto.Product;
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
	public String list(Model model) {
		List list = productService.selectAllProduct();
		model.addAttribute("list",list);
		return "/product/list";
	}
	
	@GetMapping(value="/writerFrm")
	public String writerFrm() {
		return "/product/writerFrm";
	}
	
	@PostMapping(value="/insert")
	public String insert(Product p, MultipartFile upfile,Model model) {
		String savepath = root+"/product/main/";
		String filepath = fileUtils.upload(savepath, upfile);
		p.setProductImg(filepath);
		int result = productService.productInsert(p);
		if(result>0) {
			model.addAttribute("title","작성완료");
			model.addAttribute("msg","게시글이 작성되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","작성실패");
			model.addAttribute("msg","문제가 발생하였습니다.");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/product/list");
		return "common/msg";
		/*
		if(result>0) {
			//productNo를 조회해와서 productFile에 이미지 추가 등록
			Product product = productService.selectOneProduct(p.getProductTitle());
			
			List<ProductFile> fileList = new ArrayList<ProductFile>();
			if (!upfile[0].isEmpty()) {
				// C:/Temp/upload/product/main
				String savepath = root + "/product/main/";
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
				model.addAttribute("title", "작성성공!");
				model.addAttribute("msg", "공지사항 작성에 성공했습니다.");
				model.addAttribute("icon", "success");
				model.addAttribute("loc", "/product/list");
				return "common/msg";
			}
		}
		*/
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage",produces="plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/product/detail/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/product/detail/"+filepath;
	}
	
	@GetMapping(value="/view")
	public String view(int productNo,Model model) {
		Product p = productService.selectOneProduct(productNo);
		model.addAttribute("p", p);
		return "/product/view";
	}
		
}
