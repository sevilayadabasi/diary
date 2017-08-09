package com.sadabasi.diary.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Spring boot startup config
 * 
 * @author sevilay.adabasi
 * */

@SpringBootApplication
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled=true)
@EnableJpaRepositories(basePackages = "com.sadabasi.diary.dao")
@EntityScan(basePackages="com.sadabasi.diary.domain")
@ComponentScan(basePackages = "com.sadabasi.diary")
public class DiaryAppConfig {

	public static void main(String[] args) {
		SpringApplication.run(DiaryAppConfig.class, args);
	}

}
