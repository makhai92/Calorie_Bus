package kr.co.caloriebus.product.controller;

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
		System.out.println(p);
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
	
	@GetMapping(value="delete")
	public String delete(int productNo,Model model) {
		int result = productService.deleteProduct(productNo);
		if(result>0) {
			model.addAttribute("title","삭제 완료");
			model.addAttribute("msg","게시물이 삭제되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","삭제 실패");
			model.addAttribute("msg","게시물 삭제 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			model.addAttribute("icon","warning");
		}
		model.addAttribute("loc","/product/list");
		return "common/msg";
	}
	
	@GetMapping(value="updateFrm")
	public String updateFrm(int productNo,Model model) {
		Product p = productService.selectOneProduct(productNo);
		model.addAttribute("p",p);
		return "/product/updateFrm";
	}
		
	@PostMapping(value="update")
	public String update(Product p, MultipartFile upfile,Model model) {
		int result = 0;
		if(upfile.isEmpty()) {
			result = productService.update1Product(p);
		}else {
			String savepath = root+"/product/main/";
			String filepath = fileUtils.upload(savepath, upfile);
			p.setProductImg(filepath);
			result = productService.update2Product(p);
		}
		if(result>0) {
			model.addAttribute("title","수정완료");
			model.addAttribute("msg","게시물 수정이 완료되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","수정실패");
			model.addAttribute("msg","게시물 수정 중 문제가 발생하였습니다. 잠시 후 다시 시도해주세요.");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/product/view?productNo="+p.getProductNo());
		return "common/msg";
	}
	
	@GetMapping(value="funding")
	public String funding(int productNo,Model model) {
		Product p = productService.selectOneProduct(productNo);
		model.addAttribute("p",p);
		return "/product/funding";
	}
}
