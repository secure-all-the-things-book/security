package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("defaultsfc")
@Configuration
class BootsDefaultSecurityFilterChainConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security //
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(a -> a.anyRequest().authenticated())
			.build();
	}

}
