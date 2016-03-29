package cn.accessbright.blade.web;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.View;

import cn.accessbright.blade.SpringBootTest;
import cn.accessbright.blade.web.controller.UserController;

public class UserControllerTest extends SpringBootTest {

	@Autowired
	UserController controller;

	@Autowired
	ApplicationContext context;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("=======================HttpMessageConverters===================================");
		Map<String, HttpMessageConverter> messageConverters = context.getBeansOfType(HttpMessageConverter.class);
		for (Map.Entry<String, HttpMessageConverter> entry : messageConverters.entrySet()) {
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
		System.out.println("=======================Views===================================");
		Map<String, View> views = context.getBeansOfType(View.class);
		for (Map.Entry<String, View> entry : views.entrySet()) {
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
	}

}
