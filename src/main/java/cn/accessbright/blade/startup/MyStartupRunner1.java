package cn.accessbright.blade.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order
@Component
public class MyStartupRunner1 implements CommandLineRunner {
	private static Logger logger=LoggerFactory.getLogger(MyStartupRunner1.class);
	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载缓存数据等操作<<<<<<<<<<<<<");
	}
}