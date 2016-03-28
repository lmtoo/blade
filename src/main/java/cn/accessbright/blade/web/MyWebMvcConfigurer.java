package cn.accessbright.blade.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.accessbright.blade.web.interceptor.MyInterceptor;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/").addResourceLocations("");
//		super.addResourceHandlers(registry);
//	}
}
