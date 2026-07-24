package com.example.security.authorization.requests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Profile("simpleauthorization")
@Configuration
class ProfileControllerConfiguration {

	@Bean
	Customizer<HttpSecurity> httpSecurityCustomizer() {
		return http -> http.authorizeHttpRequests(a -> a
			// <.>
			.requestMatchers(ProfileController.ADMIN_PATH)
			.hasRole("ADMIN")
			// <.>
			.requestMatchers(ProfileController.USER_PATH)
			.authenticated());
	}

}