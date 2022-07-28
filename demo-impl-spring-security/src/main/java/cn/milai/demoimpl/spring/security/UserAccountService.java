package cn.milai.demoimpl.spring.security;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  自定义了 UserDetialsService 后，默认的 AuthenticationProvider 不再起作用
 */
@Service
public class UserAccountService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User(username, "0123", Arrays.asList(new SimpleGrantedAuthority("admin")));
		return user;
	}

}
