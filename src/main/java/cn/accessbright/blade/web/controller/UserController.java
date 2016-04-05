package cn.accessbright.blade.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.system.User;
import cn.accessbright.blade.repository.UserRepository;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@RequestMapping("/create")
	public User createUsers() {
		User user = new User();
		user.setUsername("lmt");
		user.setPassword("60123");
		user.setActiveCode(UUID.randomUUID().toString());

		return repository.save(user);
	}

	@RequestMapping("/findByActiveCode")
	public User findByUUID(String activeCode){
		return repository.findByActiveCode(activeCode);
	}
}
