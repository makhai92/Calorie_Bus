package kr.co.caloriebus.util;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.caloriebus.member.model.dto.Member;

public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		if(m == null) {
			response.sendRedirect("/member/loginMsg");
			return false;
		}else {
			if(m.getMemberLevel() == 1) {
				return true;
			}else {
				response.sendRedirect("/admin/adminMsg");
				return false;
			}
		}
	}
	
}
