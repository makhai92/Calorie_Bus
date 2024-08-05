package kr.co.caloriebus.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
	private String message;              // 사용자에게 전달할 메시지
    private String redirectUrl;          // 리다이렉트
}
