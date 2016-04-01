package cn.accessbright.blade.security.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
//		 .csrf()
//	     .csrfTokenRepository(csrfTokenRepository()).and()
	     .authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/index")
					.failureUrl("/login?error")
					.permitAll()
			.and()
			.logout().permitAll();

		//		http.authorizeRequests()
//			.antMatchers("/admin/**").hasRole("ADMIN")
//			.antMatchers("/user/**").hasAnyRole("ADMIN","USER")
//			.anyRequest().authenticated();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	
//	private CsrfTokenRepository csrfTokenRepository() { 
//	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
//	    repository.setSessionAttributeName("_csrf");
//	    return repository; 
//	}
}
