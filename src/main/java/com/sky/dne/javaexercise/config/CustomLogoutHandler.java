package com.sky.dne.javaexercise.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.sky.dne.javaexercise.util.RestTemplateHelper;

@Component
public class CustomLogoutHandler implements LogoutHandler {

	@Override
	public void logout(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, Authentication authentication) {
		RestTemplateHelper.token = null;
		SecurityContextHolder.clearContext();

	}
}
