package com.sadabasi.diary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.sadabasi.diary.config.DiaryAppConfig;
import com.sadabasi.diary.domain.Entry;
import com.sadabasi.diary.domain.User;
import com.sadabasi.diary.domain.UserRole;
import com.sadabasi.diary.util.SpringUtil;

/**
 * Tests for Service classes
 * 
 * @author sevilay.adabasi
 * */

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DiaryAppConfig.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/import.sql")
public class ServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	EntryService entryService;

	@Test
	public void addUserTest() {
		User user = addNewUser("servicenew@gmail.com");
		//check if created
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void findUserRole() {
		User user = addNewUser("servicefindrole@gmail.com");
		//check if created
		assertThat(user.getId()).isNotNull();
		
		//get userrole user must have a role when created
		List<UserRole> userRoles = userService.findUserRoles(user);
		assertThat(userRoles.size()).isGreaterThan(0);
	}
	
	private User addNewUser(String email) {
		User user = new User(null, email, "Sevilay", "Adabaşı", "Emrah",SpringUtil.getPasswordEncoder().encode("test"));
		userService.addUser(user);
		return user;
	}
	
	@Test
	public void addEntryTest() {
		//firstly add a user
		User user = addNewUser("servicenewentry@gmail.com");
		assertThat(user.getId()).isNotNull();
		
		//add new entry
		Entry entry = entryService.addEntry(user, "Just an entry");
		
		assertThat(entry.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void updateEntryTest() {
		User user = addNewUser("serviceupdateentry@gmail.com");
		assertThat(user.getId()).isNotNull();

		//add an entry
		Entry entry = entryService.addEntry(user, "Just an entry");
		
		//update entry
		entryService.updateEntry(entry.getId(), "NewEntry");
		
		//get entry again
		entry = entryService.findEntry(entry.getId());
		
		assertThat(entry.getText()).isEqualTo("NewEntry");
	}
	
	@Test
	@Transactional
	public void deleteEntryTest() {
		//add user
		User user = addNewUser("servicedeleteentry@gmail.com");
		assertThat(user.getId()).isNotNull();

		//add an entry
		Entry entry = entryService.addEntry(user, "Just an entry");
		
		//delete it
		entryService.deleteEntry(entry.getId());
		
		//check if exist
		entry = entryService.findEntry(entry.getId());
		
		assertThat(entry).isNull();
	}
	

}
