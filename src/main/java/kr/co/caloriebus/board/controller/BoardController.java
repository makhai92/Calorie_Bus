package kr.co.caloriebus.board.controller;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardFile;
import kr.co.caloriebus.board.model.dto.BoardListData;
import kr.co.caloriebus.board.model.service.BoardService;
import kr.co.caloriebus.member.model.dto.Member;
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
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/board/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/board/editor/"+filepath;
	}
	
	@PostMapping(value="/write")
	public String write(Board b,MultipartFile[] files, Model model) {
		ArrayList<BoardFile> fileList = new ArrayList<BoardFile>();
		if(!files[0].isEmpty()) {
			String savepath = root+"/board/";
			for(int i=0;i<files.length;i++) {
				String filename = files[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, files[i]);
				BoardFile boardFile = new BoardFile();
				boardFile.setFilepath(filepath);
				boardFile.setFilename(filename);
				fileList.add(boardFile);
			}
		}
		int result = boardService.insertBoard(b,fileList);
		if(result>0) {
			model.addAttribute("title","게시글 작성 성공");
			model.addAttribute("msg","게시글 작성 성공");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","게시글 작성 실패");
			model.addAttribute("msg","게시글 작성 실패");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/board/list?category="+b.getBoardCategory()+"&reqPage=1");
		return "common/msg"; 
	}
	
	@GetMapping(value="/view")
	public String view(int boardNo,@SessionAttribute (required=false)Member m,Model model) {
		Board b = null;
		if(m != null) {
			b = boardService.selectBoard(m.getMemberNo(),boardNo);			
		}else {
			b = boardService.selectBoard(-1,boardNo);
		}
		model.addAttribute("board",b);
		return "board/view";
	}
}
