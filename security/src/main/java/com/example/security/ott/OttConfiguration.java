package com.example.security.ott;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
class OttConfiguration {

	static final String SENT_URL = "/login/ott/sent";

	@Bean
	Customizer<HttpSecurity> ottCustomizer(ConsoleOneTimeTokenGenerationSuccessHandler handler) {
		return security -> security // <.>
			.oneTimeTokenLogin(config -> config //
				.tokenGenerationSuccessHandler(handler))//
			.authorizeHttpRequests(a -> a
				// <.>
				.requestMatchers(SENT_URL)
				.permitAll()); //
	}

}
