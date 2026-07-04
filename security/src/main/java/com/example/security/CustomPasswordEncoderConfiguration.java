package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;

import java.util.Map;

// <.>
@Configuration
@Profile("password4j")
class CustomPasswordEncoderConfiguration {

	@Bean
	DelegatingPasswordEncoder delegatingPasswordEncoder() {
		var argon = new Argon2Password4jPasswordEncoder();
		var defaultPasswordEncoder = "argon@Password4j";
		var mapOfEncoders = Map.of(defaultPasswordEncoder, argon, "bcrypt",
				(PasswordEncoder) new BCryptPasswordEncoder());
		return new DelegatingPasswordEncoder(defaultPasswordEncoder, mapOfEncoders);
	}

}
