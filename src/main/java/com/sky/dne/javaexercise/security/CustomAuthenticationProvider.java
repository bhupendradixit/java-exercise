package com.sky.dne.javaexercise.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.sky.dne.javaexercise.dto.UserDto;
import com.sky.dne.javaexercise.model.AuthenticationResponse;
import com.sky.dne.javaexercise.util.Constants;
import com.sky.dne.javaexercise.util.RestTemplateHelper;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private RestTemplateHelper restTemplateHelper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
//		authentication.getPrincipal()

		UserDto user = new UserDto();
		user.setEmail(username);
		user.setPassword(password);

		AuthenticationResponse authResponse = restTemplateHelper.postForEntity(AuthenticationResponse.class,
				Constants.LOGIN_URL, user, null);

		if (authResponse == null || !authResponse.getMessage().equalsIgnoreCase("success")) {
			throw new BadCredentialsException("Invalid username or password");
		}
		RestTemplateHelper.token = authResponse.getData().getToken();

		return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
