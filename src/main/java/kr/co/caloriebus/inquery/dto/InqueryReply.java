package kr.co.caloriebus.inquery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InqueryReply {
	private int replyNo;
	private String replyContent;
	private String replyDate;
	private int inqueryNo;
	private int inqueryRef;
	private int inqueryReplyRef;
	}
