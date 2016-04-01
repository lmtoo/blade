package cn.accessbright.blade.security.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.accessbright.blade.domain.system.SystemUserHolder;
import cn.accessbright.blade.domain.system.User;
import cn.accessbright.blade.repository.UserRepository;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return new SystemUserHolderImpl(user);
	}

	private static class SystemUserHolderImpl extends org.springframework.security.core.userdetails.User implements SystemUserHolder {
		private User user;

		public SystemUserHolderImpl(User user) {
			super(user.getUsername(), user.getPassword(), getGrantedAuthoritis(user));
			this.user = user;
		}

		private static List<GrantedAuthority> getGrantedAuthoritis(User user) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			Set<String> roleNames = user.getRoleNames();
			for (String roleName : roleNames) {
				authorities.add(new SimpleGrantedAuthority(roleName));
			}
			return authorities;
		}

		@Override
		public User getSystemUser() {
			return this.user;
		}
	}
}