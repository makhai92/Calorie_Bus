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

import jakarta.servlet.http.HttpServletResponse;
import kr.co.caloriebus.board.model.dto.Board;
import kr.co.caloriebus.board.model.dto.BoardComment;
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
	public String write(Board b,MultipartFile[] upfile, Model model) {
		ArrayList<BoardFile> fileList = new ArrayList<BoardFile>();
		if(!upfile[0].isEmpty()) {
			System.out.println(1);
			String savepath = root+"/board/";
			for(int i=0;i<upfile.length;i++) {
				String filename = upfile[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, upfile[i]);
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
	public String view(int boardNo,String check,@SessionAttribute (required=false)Member member,Model model) {
		Board b = null;
		if(member != null) {
			b = boardService.selectBoard(member.getMemberNo(),boardNo,check);	
			model.addAttribute("memberNo",member.getMemberNo());
		}else {
			b = boardService.selectBoard(-1,boardNo,check);
			model.addAttribute("memberNo",-1);
		}
		model.addAttribute("board",b);
		return "board/view";
	}
	
	@ResponseBody
	@PostMapping(value="/boardLikePush")
	public int boardLikePush(int boardNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = boardService.boardLikePush(boardNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	@ResponseBody
	@PostMapping(value="/boardCommentLikePush")
	public int boardCommentLikePush(int boardCommentNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = boardService.boardCommentLikePush(boardCommentNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	@GetMapping(value="/filedown")
	public void filedown(BoardFile bf,HttpServletResponse response) {
		String savepath = root+"/board/";
		fileUtils.downloadFile(savepath, bf.getFilename(), bf.getFilepath(), response);
	}
	
	@PostMapping(value="/insertBoardComment")
	public String insertBoardComment(BoardComment bc,@SessionAttribute(required=false)Member member) {
		bc.setMemberNo(member.getMemberNo());
		int result = boardService.insertBoardComment(bc);
		return "redirect:/board/view?boardNo="+bc.getBoardRef();
	}
	
	@ResponseBody
	@PostMapping(value="/boardReCommentList")
	public List boardReCommentList(int boardCommentNo,int memberNo){
		List list = boardService.boardReCommentList(boardCommentNo,memberNo);
		return list;
	}
	@PostMapping(value="/updateComment")
	public String updateComment(BoardComment bc){
		int result  = boardService.updateComment(bc);
		return "redirect:/board/view?boardNo="+bc.getBoardRef();
	}
	@GetMapping(value="/deleteComment")
	public String deleteComment(int boardCommentNo,int boardNo) {
		int result = boardService.deleteComment(boardCommentNo);
		return "redirect:/board/view?boardNo="+boardNo;
	}
	
	// 마이페이지 용 내 게시글 보기
	@GetMapping(value="/myboard")
	public String myboard(Model model, @SessionAttribute Member member, int reqPage) {
		int memberNo = member.getMemberNo();
		BoardListData bld = boardService.selectMyBoardList(memberNo, reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		model.addAttribute("category", "myboard");
		return "member/myboard";
	}
}
