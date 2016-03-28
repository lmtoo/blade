package cn.accessbright.blade.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import cn.accessbright.blade.repository.UserRepository;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {

	@Autowired
	private UserRepository repository;

	@Override
	public User getCurrentAuditor() {
		User user =repository.findOne(1);
		return  user;
	}
}
