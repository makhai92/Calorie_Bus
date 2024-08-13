package kr.co.caloriebus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.caloriebus.util.AdminInterceptor;
import kr.co.caloriebus.util.LoginInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	@Value("${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/template/","classpath:/static/");
		registry.addResourceHandler("/board/editor/**").addResourceLocations("file:///"+root+"/board/editor/");
		registry.addResourceHandler("/newsletter/**").addResourceLocations("file:///"+root+"/newsletter/");
		registry.addResourceHandler("/product/detail/**").addResourceLocations("file:///"+root+"/product/detail/");
		registry.addResourceHandler("/product/main/**").addResourceLocations("file:///"+root+"/product/main/");
		registry.addResourceHandler("/product/review/**").addResourceLocations("file:///"+root+"/product/review/"); 
		registry.addResourceHandler("/inquery/inqueryEditor/**").addResourceLocations("file:///"+root+"/inquery/inqueryEditor/");
		registry.addResourceHandler("/exercise/editor/**").addResourceLocations("file:///"+root+"/exercise/editor/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/board/**",
								"/member/logout",
								"/member/updatePw",
								"/member/mypage",
								"/member/updateMember",
								"/member/deleteAccount",
								"/member/myfunding",
								"/member/myfundingView",
								"/member/mylike",
								"/member/myinquery",
								"/member/myboard",
								"/product/fundingFrm",
								"/product/reviewFrm",
								"/product/reviewDelete",
								"/rulletPage/insertEventItem",
								"/newsletter/filedown",
								"/newsletter/updateComment",
								"/newsletter/deleteComment",
								"/newsletter/insertNewsLetterComment",
								"/attendance/attendance",
								"/inquery/**",
								"/exercise/insertExerciseComment",
								"/exercise/updateComment",
								"/exercise/deleteComment"
								)
				.excludePathPatterns("/board/list",
									"/board/search",
									"/board/view",
									"/board/boardLikePush",
									"/board/boardCommentLikePush",
									"/board/boardReCommentList",
									"/product/likePush",
									"/inquery/insertReply",
									"/inquery/deleteReply",
									"/inquery/updateReply"
									);
		registry.addInterceptor(new AdminInterceptor())
								.addPathPatterns(
												"/admin/**",
												"/product/writerFrm",
												"/product/updateFrm",
												"/product/delete",
												"/newsletter/writeForm",
												"/newsletter/editForm",
												"/newsletter/delete",
												"/faq/update",
												"/faq/write",
												"/faq/faqWriter",
												"/faq/faqDelete",
												"/faq/faqUpdate",
												"/inquery/insertReply",
												"/inquery/deleteReply",
												"/inquery/updateReply",
												"/exercise/write",
												"/exercise/delete",
												"/exercise/updateFrm",
												"/exercise/editFrm",
												"/exercise/update"
												)
								.excludePathPatterns("/admin/adminMsg");
	}
	
}
