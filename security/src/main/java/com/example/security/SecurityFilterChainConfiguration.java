package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("sfc")
class SecurityFilterChainConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security //
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults())
			.csrf(Customizer.withDefaults()) //
			.cors(AbstractHttpConfigurer::disable) //
			.authorizeHttpRequests(a -> a //
				.requestMatchers("/admin")
				.hasRole("ADMIN")//
				.requestMatchers("/**")
				.authenticated() //
			)//
			.build();
	}

}
