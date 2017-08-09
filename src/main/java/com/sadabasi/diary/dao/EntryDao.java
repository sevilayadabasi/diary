package com.sadabasi.diary.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sadabasi.diary.domain.Entry;
import com.sadabasi.diary.domain.User;

/**
 * DAO for Entry
 * 
 * @author sevilay.adabasi
 * */

@Transactional
@Repository
public interface EntryDao extends CrudRepository<Entry, Long> {

  /**
   * Return entries created by given user
   * 
   * @param user who is created entries
   */
  public List<Entry> findByUser(User user);

}