package kr.co.caloriebus.inquery.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.co.caloriebus.util.FileUtils;
import kr.co.caloriebus.inquery.dto.InqueryListData;
import kr.co.caloriebus.inquery.service.InqueryService;

@Controller
@RequestMapping(value = "/inquery")
public class InqueryController {
	@Autowired
	private InqueryService inqueryService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils FileUtils;
	
	@GetMapping(value = "/inqueryMain")
	public String list(Model model,int reqPage) {
		InqueryListData ild = inqueryService.selectInqueryList(reqPage);
		model.addAttribute("list", ild.getList());
		model.addAttribute("pageNavi",ild.getPageNavi());
		return "inquery/inqueryMain";
	}
	@GetMapping(value="/inqueryWriter")
	public String inqueryWriter() {
		return "inquery/inqueryWriter";
	}
}
