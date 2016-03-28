package cn.accessbright.blade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ServletComponentScan // 开启自动注册servlet组件的功能
@EnableJpaAuditing // 开启spring-data-jpa的审计功能
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}