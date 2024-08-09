package kr.co.caloriebus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	@Value("${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/template/","classpath:/static/");
		registry.addResourceHandler("/board/editor/**").addResourceLocations("file:///"+root+"/board/editor/");
		registry.addResourceHandler("/newsletter/editor/**").addResourceLocations("file:///"+root+"/newsletter/editor/");
		registry.addResourceHandler("/product/detail/**").addResourceLocations("file:///"+root+"/product/detail/");
		registry.addResourceHandler("/product/main/**").addResourceLocations("file:///"+root+"/product/main/");
		registry.addResourceHandler("/product/review/**").addResourceLocations("file:///"+root+"/product/review/"); 
		registry.addResourceHandler("/inquery/inqueryEditor/**").addResourceLocations("file:///"+root+"/inquery/inqueryEditor/");
		registry.addResourceHandler("/exercise/editor/**").addResourceLocations("file:///"+root+"/exercise/editor/");
	}
}
