package com.sadabasi.diary.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sadabasi.diary.domain.User;
import com.sadabasi.diary.domain.UserRole;

/**
 * @author erdal.bitik
 * */

@Transactional
@Repository
public interface UserRoleDao extends CrudRepository<UserRole, Long> {
	
	public List<UserRole> findByUser(User user);

}