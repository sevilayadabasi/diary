package com.sadabasi.diary.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sadabasi.diary.domain.User;

/**
 * DAO for User
 * 
 * @author sevilay.adabasi
 * */

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Long> {

  /**
   * Return a user by given email or null if no user is found.
   * 
   * @param email the user email.
   */
  public User findByEmail(String email);

}