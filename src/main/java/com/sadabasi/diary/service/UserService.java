package com.sadabasi.diary.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sadabasi.diary.config.DiaryException;
import com.sadabasi.diary.dao.UserDao;
import com.sadabasi.diary.dao.UserRoleDao;
import com.sadabasi.diary.domain.User;
import com.sadabasi.diary.domain.UserRole;
import com.sadabasi.diary.util.SecurityUtil;

/**
 * User Service
 * 
 * @author sevilay.adabasi
 * */

@Service
public class UserService {

	@Autowired
	UserDao dao;

	@Autowired
	UserRoleDao userRoleDao;

	public User findByEmail(String email) {
		if(StringUtils.isEmpty(email)) {
			return null;
		}
		return dao.findByEmail(email);
	}

	@Transactional
	public void addUser(User user) {
		String email = user.getEmail();

		if(StringUtils.isEmpty(email)) {
			throw new DiaryException("message.register.emailRequired");
		}

		User orgUser = findByEmail(email);
		if(Objects.nonNull(orgUser)) {
			throw new DiaryException("message.register.alreadyExist");
		}

		dao.save(user);

		//add a default user role 
		UserRole ur = new UserRole(SecurityUtil.DEFAULT_ROLE);
		ur.setUser(user);
		userRoleDao.save(ur);
	}
	
	public void deleteUser(User user) {
		//delete user roles
		List<UserRole> userRoles = findUserRoles(user);
		userRoleDao.delete(userRoles);
		//delete user
		dao.delete(user.getId());
	}

	public List<UserRole> findUserRoles(User user) {
		return userRoleDao.findByUser(user);
	}

	/**
	 * used for test purposes. 
	 * */
	@Secured("ROLE_USER")
	public String secureMethod() {
		return "Security";
	}

}
