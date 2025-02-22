package kr.co.caloriebus.newsletter.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping(value="/privacy")
	public String privacy() {
		return "newsletter/privacy";
	}
	@GetMapping(value="/terms")
	public String terms() {
		return "newsletter/terms";
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
		System.out.println(n);
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
	public String edit(NewsLetter nl, MultipartFile[] upfile,int[] delFileNo ,@SessionAttribute(required=false) Member member, Model model) {
	    if (member == null || nl.getMemberNo() != member.getMemberNo()) {
	        return "redirect:/newsletter/list?reqPage=1";
	    }
	    ArrayList<NewsLetterFile> fileList = new ArrayList<>();
	    if (!upfile[0].isEmpty()) {
	        String savepath = root + "/newsletter/";
	        for (int i = 0; i < upfile.length; i++) {
	            String filename = upfile[i].getOriginalFilename();
	            String filepath = fileUtils.upload(savepath, upfile[i]);
	            NewsLetterFile newsletterFile = new NewsLetterFile();
	            newsletterFile.setFilepath(filepath);
	            newsletterFile.setFilename(filename);
	            fileList.add(newsletterFile);
	        }
	    }
	    List<NewsLetterFile> delFileList = newsletterService.updateNewsLetter(nl, fileList,delFileNo);
	    if (delFileList != null) {
	    	for(NewsLetterFile newsletterFile : delFileList) {
	    		String savepath = root+"/newsletter/";
	    		File delFile = new File(savepath+newsletterFile.getFilepath());
	    		delFile.delete();
	    	}
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
    @PostMapping(value="/updateComment")
	public String updateComment(NewsLetterComment nlc){
		int result  = newsletterService.updateComment(nlc);
		System.out.println(nlc);
		System.out.println(result);
		return "redirect:/newsletter/viewForm?boardNo="+nlc.getBoardRef()+"&check=1";
	}
	@GetMapping(value="/deleteComment")
	public String deleteComment(int boardCommentNo,int boardNo) {
		int result = newsletterService.deleteComment(boardCommentNo);
		return "redirect:/newsletter/viewForm?boardNo="+boardNo+"&check=1";
	}
    @GetMapping(value="/search")
    public String search(@RequestParam(defaultValue = "1") int reqPage, String keyword, Model model) {
        NewsLetterListData bld = newsletterService.searchNewsLetterList(keyword, reqPage);
        model.addAttribute("list", bld.getList());
        model.addAttribute("pageNavi", bld.getPageNavi());
        model.addAttribute("keyword", keyword);
        return "newsletter/list";
    }
}
