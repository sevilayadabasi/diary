package com.sadabasi.diary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.sadabasi.diary.service.LoginService;

/**
 * Custom AuthenticationProvider for Spring security
 * 
 * @author sevilay.adabasi
 * */
@Component("authenticationProvider")
public class DiaryAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private LoginService loginService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return loginService.authenticate(authentication);
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
