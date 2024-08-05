package kr.co.caloriebus.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.board.model.service.BoardService;
import kr.co.caloriebus.util.FileUtils;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/list")
	public String list(String category,int reqPage,Model model) {
		BoardListData bld = boardService.selectBoardList(category,reqPage);
		model.addAttribute("list",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		model.addAttribute("category",category);
		return "board/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "board/writeFrm";
	}
}
