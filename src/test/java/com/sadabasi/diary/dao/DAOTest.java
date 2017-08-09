package com.sadabasi.diary.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.sadabasi.diary.util.SpringUtil;

/**
 * Tests for userdao and Entry Dao
 * 
 * @author sevilay.adabasi
 * */

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DiaryAppConfig.class})
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/import.sql")
public class DAOTest {

	@Autowired
	UserDao userRepository;
	
	@Autowired
	EntryDao entryRepository;

	@Test
	public void addUserTest() {
		User user = addNewUser("sevilaynew@gmail.com");
		//check if created
		assertThat(user.getId()).isNotNull();
	}
	
	@Test
	public void updateUserTest() {
		User user = addNewUser("sevilayupdate@gmail.com");
		assertThat(user.getId()).isNotNull();

		//get same user
		User findByEmail = userRepository.findByEmail(user.getEmail());
		//change name
		findByEmail.setName("TestName");
		findByEmail = userRepository.save(findByEmail);
		
		assertThat(findByEmail.getName()).isEqualTo("TestName");
	}
	
	@Test
	public void deleteUserTest() {
		User user = addNewUser("sevilaydelete@gmail.com");
		assertThat(user.getId()).isNotNull();

		//delete user
		userRepository.delete(user);
		
		//check if exist
		User findByEmail = userRepository.findByEmail(user.getEmail());
		
		assertThat(findByEmail).isNull();
	}
	
	private User addNewUser(String email) {
		User user = new User(null, email, "Sevilay", "Adabaşı", SpringUtil.getPasswordEncoder().encode("test"), email);
		return userRepository.save(user);
	}
	
	@Test
	public void addEntryTest() {
		//firstly add a user
		User user = addNewUser("sevilaynewentry@gmail.com");
		
		Entry entry = new Entry("Just an entry");
		entry.setUser(user);
		entryRepository.save(entry);
		
		assertThat(entry.getId()).isNotNull();
	}
	
	@Test
	public void updateEntryTest() {
		User user = addNewUser("sevilayupdateentry@gmail.com");
		assertThat(user.getId()).isNotNull();

		//add an entry
		Entry entry = new Entry("Just an entry");
		entry.setUser(user);
		entryRepository.save(entry);
		
		//get entry from db
		entry = entryRepository.findOne(entry.getId());
		assertThat(entry).isNotNull();
		
		//change text
		entry.setText("newtext");
		entry = entryRepository.save(entry);
		
		//get entry again
		entry = entryRepository.findOne(entry.getId());
		assertThat(entry.getText()).isEqualTo("newtext");
	}
	
	@Test
	@Transactional
	public void deleteEntryTest() {
		//add user
		User user = addNewUser("sevilaydeleteentry@gmail.com");
		assertThat(user.getId()).isNotNull();

		//add an entry
		Entry entry = new Entry("Just an entry");
		entry.setUser(user);
		entryRepository.save(entry);
		
		//delete it
		entryRepository.delete(entry);
		
		//check if exist
		entry = entryRepository.findOne(entry.getId());
		
		
		assertThat(entry).isNull();
	}
	

}
