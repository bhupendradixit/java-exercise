package com.sky.dne.javaexercise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sky.dne.javaexercise.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	/*
	 * @Autowired private LogoutHandler customLogoutHandler;
	 */
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers("/login", "/register").permitAll().anyRequest().authenticated()
				/*
				 * .anyRequest() .authenticated()
				 * .and().formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll()
				 */
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/list").and().logout()
//        .logoutUrl("/logout")
//        .addLogoutHandler(customLogoutHandler)
				.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll().and().sessionManagement().invalidSessionUrl("/login").and()
//            .cors().disable()
				.httpBasic();
		return http.build();
	}

}