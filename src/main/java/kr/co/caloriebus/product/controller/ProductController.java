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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.member.model.dto.Member;
import kr.co.caloriebus.product.model.dto.Funding;
import kr.co.caloriebus.product.model.dto.Product;
import kr.co.caloriebus.product.model.dto.ProductReview;
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
	
	@GetMapping(value="/ingList")
	public String list1(Model model) {
		List list = productService.selectAllProduct1();
		int state = 1;
		model.addAttribute("list",list);
		model.addAttribute("state",state);
		return "/product/ingList";
	}
	@GetMapping(value="/endList")
	public String list2(Model model) {
		List list = productService.selectAllProduct2();
		int state = 2;
		model.addAttribute("list",list);
		model.addAttribute("state",state);
		return "/product/endList";
	}
	@GetMapping(value="/notStartList")
	public String list3(Model model) {
		List list = productService.selectAllProduct3();
		int state = 3;
		model.addAttribute("list",list);
		model.addAttribute("state",state);
		return "/product/notStartList";
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
		model.addAttribute("loc","/product/notStartList");
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
	public String view(int productNo,int state,Model model,@SessionAttribute(required=false) Member member) {
		Product p = productService.selectOneProduct(productNo);
		int totalAmount = productService.orderAmount(productNo);
		int likeCount = productService.selectProductLikeCount(productNo);
		int isLike = 0;
		int memberNo = 0;
		if(member != null) {
			isLike = productService.selectIsCount(member.getMemberNo(),productNo);
			memberNo = member.getMemberNo();
		}else {
			memberNo = -1;
		}
		System.out.println(isLike);
		p.setIsLike(isLike);
		p.setLikeCount(likeCount);
		model.addAttribute("p", p);
		model.addAttribute("totalAmount",totalAmount);
		model.addAttribute("state", state);
		model.addAttribute("memberNo", memberNo);
		return "/product/view";
	}
	
	@GetMapping(value="/delete")
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
		model.addAttribute("loc","/product/ingList");
		return "common/msg";
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int productNo,int state,Model model) {
		Product p = productService.selectOneProduct(productNo);
		model.addAttribute("p",p);
		model.addAttribute("state",state);
		return "/product/updateFrm";
	}
		
	@PostMapping(value="/update")
	public String update(Product p,int state, MultipartFile upfile,Model model) {
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
		model.addAttribute("loc","/product/view?productNo="+p.getProductNo()+"&state="+state);
		return "common/msg";
	}
	
	@GetMapping(value="/fundingFrm")
	public String fundingFrm(int productNo,Model model) {
		Product p = productService.selectOneProduct(productNo);
		model.addAttribute("p",p);
		return "/product/fundingFrm";
	}
	
	@PostMapping(value="/funding")
	public String funding(Funding f,Model model) {
		int result = productService.insertFunding(f);
		if(result>0) {
			model.addAttribute("title","구매 예약 완료");
			model.addAttribute("msg","구매 예약이 완료되었습니다. 24시간 내로 미입금 시 자동 취소됩니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/product/myfunding?reqPage=1");
		}else {
			model.addAttribute("title","구매 예약 실패");
			model.addAttribute("msg","문제가 발생하였습니다.관리자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/product/inglist");
		}
		return "common/msg";
	}
	
	@GetMapping(value="/review")
	public String review(int productNo,int state,Model model) {
		Product p = productService.selectOneProduct(productNo);
		List list = productService.selectAllProductReview(productNo);
		model.addAttribute("p",p);
		model.addAttribute("list",list);
		model.addAttribute("state",state);
		return "/product/reviewList";
	}
	
	@GetMapping(value="/reviewFrm")
	public String reviewFrm(int productNo,int fundingNo,Model model) {
		Product p = productService.selectOneProduct(productNo); 
		model.addAttribute("p",p);
		model.addAttribute("fundingNo",fundingNo);
		return "/product/reviewFrm";
	}
	
	@PostMapping(value="/reviewInsert")
	public String reviewInsert(ProductReview pr,MultipartFile upfile,Model model) {
		String savepath = root+"/product/review/";
		String filepath = fileUtils.upload(savepath, upfile);
		pr.setReviewImg(filepath);
		int result = productService.reviewInsert(pr);
		int state=0;
		if(result>0) {
			state = productService.searchState(pr.getProductNo());
			model.addAttribute("title","작성완료");
			model.addAttribute("msg","게시글이 작성되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","작성실패");
			model.addAttribute("msg","문제가 발생하였습니다.");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/product/review?productNo="+pr.getProductNo()+"&state="+state);
		return "common/msg";
	}
	
	
	@GetMapping(value="/reviewUpdateFrm")
	public String reviewUpdateFrm(int fundingNo,int state,Model model) {
		ProductReview pr = productService.selecOneProductReview(fundingNo);
		model.addAttribute("pr",pr);
		model.addAttribute("state",state);
		return "/product/reviewUpdateFrm";
	}
	
	@PostMapping(value="/reviewUpdate1")
	public String reviewUpdate(ProductReview pr,int state,Model model) {
		int result = productService.reviewUpdate1(pr);
		
		if(result>0) {
			model.addAttribute("title","수정완료");
			model.addAttribute("msg","후기가 수정되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","수정실패");
			model.addAttribute("msg","문제가 발생하였습니다.");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/product/review?productNo="+pr.getProductNo()+"&state="+state);
		return "common/msg";
	}

		@PostMapping(value="/reviewUpdate2")
	public String reviewUpdate2(ProductReview pr,int state,MultipartFile upfile,Model model) {
			String savepath = root+"/product/review/";
			String filepath = fileUtils.upload(savepath, upfile);
			pr.setReviewImg(filepath);
			int result = productService.reviewUpdate2(pr);			
		
		if(result>0) {
			model.addAttribute("title","수정완료");
			model.addAttribute("msg","후기가 수정되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","수정실패");
			model.addAttribute("msg","문제가 발생하였습니다.");
			model.addAttribute("icon","error");
		}
		System.out.println(pr.getProductNo());
		model.addAttribute("loc","/product/review?productNo="+pr.getProductNo()+"&state="+state);
		return "common/msg";
	}
	
	@GetMapping(value="/reviewDelete")
	public String reviewDelete(int fundingNo,int state,Model model) {
		ProductReview pr = productService.selecOneProductReview(fundingNo);
		int result = productService.reviewDelete(fundingNo);
		if(result>0) {
			model.addAttribute("title","삭제 완료");
			model.addAttribute("msg","게시물이 삭제되었습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","삭제 실패");
			model.addAttribute("msg","게시물 삭제 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
			model.addAttribute("icon","warning");
		}
		model.addAttribute("loc","/product/review?productNo="+pr.getProductNo()+"&state="+state);
		return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="/likePush")
	public int likePush(int productNo, int isLike, @SessionAttribute(required=false) Member member)  {
		//@SessionAttribute에서 로그인정보를 가지고 올때 required옵션을 명시하지않으면 기본적으로 true
		// -> 로그인이 되어있지 않으면 에러 발생
		// -> 로그인이 되어있지 않은 상태에서 에러를 발생시키지 않으려면(required = false)옵션을 추가
		//		-> 로그인이 되어있으면 로그인한 회원정보/로그인이 되어잇지 않으면 null
		if(member == null) {
			return -10;
		}else {
			int memberNo = member.getMemberNo();
			int result = productService.likePush(productNo,isLike,memberNo);
			return result;
		}	
	}
	
	@ResponseBody
	@GetMapping(value="/searchState")
	public int searchState(int productNo) {
		System.out.println(productNo);
		int state = productService.searchState(productNo);
		System.out.println(state);
		return state;
	}
}
