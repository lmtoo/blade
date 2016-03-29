package cn.accessbright.blade.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import cn.accessbright.blade.domain.system.User;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {
	
	@Override
	public User getCurrentAuditor() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null || !authentication.isAuthenticated()) {
//			return null;
//		}
		return null;
	}
}