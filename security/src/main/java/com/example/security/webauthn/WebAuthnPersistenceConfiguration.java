package com.example.security.webauthn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.web.webauthn.management.JdbcPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.JdbcUserCredentialRepository;

@Configuration
class WebAuthnPersistenceConfiguration {

	// <.>
	@Bean
	JdbcPublicKeyCredentialUserEntityRepository jdbcPublicKeyCredentialRepository(JdbcOperations jdbc) {
		return new JdbcPublicKeyCredentialUserEntityRepository(jdbc);
	}

	// <.>
	@Bean
	JdbcUserCredentialRepository jdbcUserCredentialRepository(JdbcOperations jdbc) {
		return new JdbcUserCredentialRepository(jdbc);
	}

}
