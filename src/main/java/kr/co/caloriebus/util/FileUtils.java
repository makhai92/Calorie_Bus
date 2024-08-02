package kr.co.caloriebus.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileUtils {
	
	public String upload(String savepath,MultipartFile file) {
		String filename = file.getOriginalFilename();
		String onlyFilename = filename.substring(0, filename.lastIndexOf("."));
		String extention = filename.substring(filename.lastIndexOf("."));
		String filepath = null;
		int count = 0;
		while(true) {
			if(count == 0) {
				filepath = onlyFilename + extention;
			}else {
				filepath = onlyFilename+"_"+count+extention;
			}
			count++;
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {
				break;
			}
		}
		try {
			file.transferTo(new File(savepath+filepath));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}
}
