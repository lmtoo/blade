package cn.accessbright.blade.web;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.accessbright.blade.security.spring.ApplicationSecurity;
import cn.accessbright.blade.web.interceptor.MyInterceptor;

@Configuration
@ServletComponentScan // 开启自动注册servlet组件的功能
public class WebConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}
	
	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/").addResourceLocations("");
	// super.addResourceHandlers(registry);
	// }
}
