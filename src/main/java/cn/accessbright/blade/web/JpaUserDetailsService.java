package cn.accessbright.blade.web;

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

import cn.accessbright.blade.domain.system.User;
import cn.accessbright.blade.repository.UserRepository;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);

		List<GrantedAuthority> authorities = new ArrayList<>();
		Set<String> roleNames = user.getRoleNames();
		for (String roleName : roleNames) {
			authorities.add(new SimpleGrantedAuthority(roleName));
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}
}