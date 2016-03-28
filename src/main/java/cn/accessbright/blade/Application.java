package cn.accessbright.blade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Application {

	// @Bean
	// public ServletRegistrationBean servletRegistrationBean() {
	// return new ServletRegistrationBean(new MyServlet(), "/myservlet");
	// }
	//
	// @Bean
	// public FilterRegistrationBean filterRegistrationBean() {
	// FilterRegistrationBean filterRegistrationBean = new
	// FilterRegistrationBean();
	// filterRegistrationBean.setFilter(new MyFilter());
	// filterRegistrationBean.setUrlPatterns(Collections.singleton("/*"));
	// return filterRegistrationBean;
	// }
	//
	// @Bean
	// public ServletListenerRegistrationBean<EventListener>
	// HttpSessionEventListener() {
	// ServletListenerRegistrationBean<EventListener>
	// servletListenerRegistrationBean = new
	// ServletListenerRegistrationBean<>();
	// servletListenerRegistrationBean.setListener(new MyHttpSessionListener());
	// return servletListenerRegistrationBean;
	// }

	// /**
	// * 修改DispatcherServlet默认配置
	// *
	// * @param dispatcherServlet
	// * @return
	// */
	// @Bean
	// public ServletRegistrationBean dispatcherRegistration(DispatcherServlet
	// dispatcherServlet) {
	// ServletRegistrationBean registration = new
	// ServletRegistrationBean(dispatcherServlet);
	// // registration.getUrlMappings().clear();
	// // registration.addUrlMappings("*.do");
	// // registration.addUrlMappings("*.json");
	// return registration;
	// }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}