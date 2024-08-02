package kr.or.caloriebus.faq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Faq {
	private int faqNo;
	private String faqTitle;
	private String faqContent;
	private int memberNo;
}
