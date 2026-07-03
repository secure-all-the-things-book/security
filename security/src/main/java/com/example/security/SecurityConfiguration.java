package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
class SecurityConfiguration {

	@Bean
	JdbcUserDetailsManager jdbcUserDetailsManager(PasswordEncoder pw, DataSource dataSource) {
		var pwe = new StandardPasswordEncoder();
		IO.println(pwe.encode("pw"));
		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setEnableUpdatePassword(true);
		return userDetailsManager;
	}

}
