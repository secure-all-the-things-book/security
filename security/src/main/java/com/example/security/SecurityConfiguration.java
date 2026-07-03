package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
class SecurityConfiguration {

	@Bean
	JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setEnableUpdatePassword(true);
		return userDetailsManager;
	}

}
