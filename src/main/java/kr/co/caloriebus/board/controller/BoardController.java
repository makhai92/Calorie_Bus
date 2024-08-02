package kr.co.caloriebus.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value="/list")
	public String list(String category,int reqPage,Model model) {
		
		model.addAttribute("category",category);
		return "board/list";
	}
}
