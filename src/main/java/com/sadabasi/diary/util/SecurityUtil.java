package com.sadabasi.diary.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sadabasi.diary.domain.User;

/**
 * Utility class for Security
 * 
 * @author sevilay.adabasi
 * */

public class SecurityUtil {
	
	public static String DEFAULT_ROLE = "ROLE_USER";
	
	/**
	 * @return login User from Spring security context
	 * */
	public static User getLoginUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}

}
