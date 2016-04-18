package cn.accessbright.blade.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.IdGenerator;

import cn.accessbright.blade.domain.system.User;
import cn.accessbright.blade.repository.UserRepository;

/**
 * 用户注册服务
 * 
 * 模块：<br>
 * 描述：
 * 
 * @author 李乐 601235723@qq.com
 * @version 1.0 2016年4月1日 Copyright 2016 XXX有限公司.
 */
@Service
public class UserService extends ApplicationService {

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private IdGenerator idGenerator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Cacheable(value = "user",key = "#username")
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}

	@CacheEvict(value = "user")
	public void delete(Integer id){
		userRepository.delete(id);
	}

	@CachePut(value = "user",key = "#user.username")
	public void update(User user){
	User old=	userRepository.findOne(user.getId());
		userRepository.saveAndFlush(user);
	}

	/**
	 * 注册用户
	 * 
	 * @param request
	 */
	@Transactional
	public void register(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword);
		user.setPhone(request.getPhone());
		user.lock();// 未激活状态
		user.setActiveCode(UUID.randomUUID().toString());

//		user.setActiveCode(idGenerator.generateId());
		userRepository.save(user);
		eventPublisher.publishEvent(new RegisterEvent(user));
	}

	/**
	 * 激活用户
	 * 
	 * @param activeCode
	 */
	@Transactional
	public void activeUser(UUID activeCode) {
//		User user = userRepository.findByActiveCode(activeCode);
//		if (user != null) {
//			user.unlock();
//			userRepository.save(user);
//			eventPublisher.publishEvent(new RegisterEvent(user));
//		}
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 */
	@Transactional
	public void changePassword(ChangePasswordRequest request) {
		String credential = request.getCredential();
		User user = userRepository.findByUsername(credential);
		if (user == null) {
			throw new RuntimeException("用户不存在!");
		}
		if (!passwordEncoder.matches(request.getOriginalPassword(), user.getPassword())) {
			throw new RuntimeException("原始密码不正确!");
		}
		if (user.isLocked()) {
			throw new RuntimeException("用户被锁定或未激活！");
		}
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		eventPublisher.publishEvent(new PasswordChangedEvent(user));
	}
}