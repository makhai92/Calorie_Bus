package kr.co.caloriebus.newsletter.controller;

import java.util.ArrayList;

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
import kr.co.caloriebus.newslatter.model.dto.NewsLetter;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterComment;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterFile;
import kr.co.caloriebus.newslatter.model.dto.NewsLetterListData;
import kr.co.caloriebus.newsletter.model.service.NewsLetterService;
import kr.co.caloriebus.util.FileUtils;

@Controller
@RequestMapping(value="/newsletter")
public class NewsLetterController {
	
	@Autowired
	private NewsLetterService newsletterService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value = "/list")
	public String list(int reqPage,Model model) {
		NewsLetterListData bld = newsletterService.selectNewsLetterList(reqPage);
		model.addAttribute("list",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		return "newsletter/list";
	}
	@GetMapping(value="/writeForm")
	public String writeForm() {
		return "newsletter/writeForm";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/newsletter/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/newsletter/editor/"+filepath;
	}
	
	@PostMapping(value="/write")
	public String write(NewsLetter nl,MultipartFile[] upFile, Model model) {
		ArrayList<NewsLetterFile> fileList = new ArrayList<NewsLetterFile>();
		if(!upFile[0].isEmpty()) {
			String savepath = root+"/newsletter/";
			for(int i=0;i<upFile.length;i++) {
				String filename = upFile[i].getOriginalFilename();
				String filepath = fileUtils.upload(savepath, upFile[i]);
				NewsLetterFile newsletterFile = new NewsLetterFile();
				newsletterFile.setFilepath(filepath);
				newsletterFile.setFilename(filename);
				fileList.add(newsletterFile);
			}
		}
		int result = newsletterService.insertNewsLetter(nl,fileList);
		if(result>0) {
			model.addAttribute("title","게시글 작성 성공");
			model.addAttribute("msg","게시글 작성 성공");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","게시글 작성 실패");
			model.addAttribute("msg","게시글 작성 실패");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/newsletter/list?reqPage=1");
		return "common/msg"; 
	}
	@GetMapping(value="/viewForm")
	public String view(int boardNo,String check,@SessionAttribute (required=false)Member member,Model model) {
		NewsLetter n = null;
		if(member != null) {
			n = newsletterService.selectNewsLetter(member.getMemberNo(),boardNo,check);	
			model.addAttribute("memberNo",member.getMemberNo());
		}else {
			n = newsletterService.selectNewsLetter(-1,boardNo,check);
			model.addAttribute("memberNo",-1);
		}
		model.addAttribute("newsletter",n);
		model.addAttribute("fileList",n.getFileList());
		return "newsletter/viewForm";
	}
	
	@ResponseBody
	@PostMapping(value="/newsletterLikePush")
	public int newsletterLikePush(int boardNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = newsletterService.newsletterLikePush(boardNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	@ResponseBody
	@PostMapping(value="/newsletterCommentLikePush")
	public int newsletterCommentLikePush(int boardCommentNo,int isLike,@SessionAttribute (required=false)Member member) {
		if(member == null) {
			return -10;
		}else {
			int likeCount = newsletterService.newsletterCommentLikePush(boardCommentNo,isLike,member.getMemberNo());
			return likeCount;
		}
	}
	@GetMapping(value="/filedown")
	public void filedown(NewsLetterFile nlf,HttpServletResponse response) {
		String savepath = root+"/newsletter/";
		fileUtils.downloadFile(savepath, nlf.getFilename(), nlf.getFilepath(), response);
	}
	@PostMapping(value="/insertNewsLetterComment")
	public String insertNewsLetterComment(NewsLetterComment nlc,String boardCommentContent,@SessionAttribute(required=false)Member member) {
		if (member != null) {
	        nlc.setMemberNo(member.getMemberNo());
	        int result = newsletterService.insertNewsLetterComment(nlc);
	        if (result > 0) {
	            return "redirect:/newsletter/viewForm?boardNo=" + nlc.getBoardRef();
	        } else {
	        }
	    }
	    return "redirect:/newsletter/viewForm?boardNo=" + nlc.getBoardRef();
	}
	@GetMapping(value="/editForm")
    public String editForm(int boardNo, @SessionAttribute(required=false) Member member, Model model) {
        if (member == null) {
            return "redirect:/newsletter/list?reqPage=1";
        }
        NewsLetter n = newsletterService.selectNewsLetter(member.getMemberNo(), boardNo, null);
        if (n != null && n.getMemberNo() == member.getMemberNo()) {
            model.addAttribute("newsletter", n);
            return "newsletter/editForm";
        }
        return "redirect:/newsletter/list?reqPage=1";
    }

    @PostMapping(value="/editForm")
    public String edit(NewsLetter nl, MultipartFile[] files, @SessionAttribute(required=false) Member member, Model model) {
        if (member == null || nl.getMemberNo() != member.getMemberNo()) {
            return "redirect:/newsletter/list?reqPage=1";
        }
        ArrayList<NewsLetterFile> fileList = new ArrayList<>();
        if (!files[0].isEmpty()) {
            String savepath = root + "/newsletter/";
            for (int i = 0; i < files.length; i++) {
                String filename = files[i].getOriginalFilename();
                String filepath = fileUtils.upload(savepath, files[i]);
                NewsLetterFile newsletterFile = new NewsLetterFile();
                newsletterFile.setFilepath(filepath);
                newsletterFile.setFilename(filename);
                fileList.add(newsletterFile);
            }
        }
        int result = newsletterService.updateNewsLetter(nl, fileList);
        if (result > 0) {
            model.addAttribute("title", "게시글 수정 성공");
            model.addAttribute("msg", "게시글 수정 성공");
            model.addAttribute("icon", "success");
        } else {
            model.addAttribute("title", "게시글 수정 실패");
            model.addAttribute("msg", "게시글 수정 실패");
            model.addAttribute("icon", "error");
        }
        model.addAttribute("loc", "/newsletter/viewForm?boardNo=" + nl.getBoardNo());
        return "common/msg";
    }

    @GetMapping(value="/delete")
    public String delete(int boardNo, @SessionAttribute(required=false) Member member, Model model) {
        if (member == null) {
            return "redirect:/newsletter/list?reqPage=1";
        }
        NewsLetter n = newsletterService.selectNewsLetter(member.getMemberNo(), boardNo, null);
        if (n != null && n.getMemberNo() == member.getMemberNo()) {
        	System.out.println(boardNo);
            int result = newsletterService.deleteNewsLetter(boardNo);
            if (result > 0) {
                model.addAttribute("title", "게시글 삭제 성공");
                model.addAttribute("msg", "게시글 삭제 성공");
                model.addAttribute("icon", "success");
            } else {
                model.addAttribute("title", "게시글 삭제 실패");
                model.addAttribute("msg", "게시글 삭제 실패");
                model.addAttribute("icon", "error");
            }
        }
        model.addAttribute("loc", "/newsletter/list?reqPage=1");
        return "common/msg";
    }
}
