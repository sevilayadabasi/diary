package com.sadabasi.diary.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.sadabasi.diary.domain.User;
import com.sadabasi.diary.util.SpringUtil;

/**
 * User form bean. used as data transfer object.
 * 
 * @author sevilay.adabasi
 * */

public class UserForm {

	/**
	 * TODO : ecrypt it
	 * */
	private Long id;
	
	/**
	 * email field is used as username
	 * */
	@NotBlank(message="message.user.emailRequired")
	@Email(message="message.user.emailNotValid")
	private String email;

	/**
	 * name field can keep some information(name,surname) about user
	 * */
	@NotBlank(message="message.user.nameRequired")
	private String name;
	
	@NotBlank(message="message.user.surnameRequired")
	private String surname;
	
	@NotBlank(message="message.user.secondNameRequired")
	private String secondName;
	
	@NotBlank(message="message.user.passwordRequired")
	private String password;
	
	@NotBlank(message="message.user.rePasswordRequired")
	private String rePassword;
	

	public UserForm() {
		
	}
	
	public User getAsUser() {
		String passwordHash = null;
		if(!StringUtils.isEmpty(password)) {
			passwordHash = SpringUtil.getPasswordEncoder().encode(password);
		}
		User user  = new User(id, email, name, surname,secondName,passwordHash);
		return user;
	}
	
	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
    public void setsecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public String getsecondName() {
    	return secondName;
   }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
}