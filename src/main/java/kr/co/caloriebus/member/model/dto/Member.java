package kr.co.caloriebus.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberPhone;
	private String memberAddr;
	private String memberEmail;
	private String memberBirth;
	private int memberLevel;
	private int eventCount;
	private String memberAccount;
	private String memberBank;
	private String memberPostcode;
}
