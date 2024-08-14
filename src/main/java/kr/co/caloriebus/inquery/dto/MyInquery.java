package kr.co.caloriebus.inquery.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyInquery {
	private int inqueryNo;
	private String inqueryTitle;
	private int replyNo;
	private String inqueryDate;
	private int memberNo;
	private boolean hasReply;
	private String inqueryWriter;
	private List<InqueryFile> fileList;
	private List<InqueryReply> replyList;
}

		


