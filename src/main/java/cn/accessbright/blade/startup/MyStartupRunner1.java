package cn.accessbright.blade.startup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.accessbright.blade.domain.system.User;
import cn.accessbright.blade.repository.UserRepository;

@Order
@Component
public class MyStartupRunner1 implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);

//	@Autowired
//	private UserRepository repository;

	@Override
	public void run(String... args) throws Exception {
//		List<User> internalUsers = repository.findUserByIsInternal();
//		if(internalUsers==null||internalUsers.isEmpty()){
//			User user=new User();
//			user.setUsername("admin");
//			user.setPassword("admin");
//			user.setInternal(true);
//			repository.save(user);
//		}
	}
}