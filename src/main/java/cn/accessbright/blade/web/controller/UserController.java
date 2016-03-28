package cn.accessbright.blade.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.User;
import cn.accessbright.blade.repository.UserRepository;

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
		return repository.save(user);
	}
}
