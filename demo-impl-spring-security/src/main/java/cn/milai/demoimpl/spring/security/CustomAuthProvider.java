package cn.milai.demoimpl.spring.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider extends DaoAuthenticationProvider {

	public CustomAuthProvider(UserDetailsService udService) {
		setUserDetailsService(udService);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
		UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (!userDetails.getPassword().equals(authentication.getCredentials().toString())) {
			throw new BadCredentialsException(userDetails.getPassword());
		}
	}
}
