package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;

@Configuration
@EnableMultiFactorAuthentication(authorities = {})
class MfaConfiguration {

	@Bean
	Customizer<HttpSecurity> httpSecurityCustomizer() {
		return security -> {
			var amf = AuthorizationManagerFactories.multiFactor()
				.requireFactors(FactorGrantedAuthority.PASSWORD_AUTHORITY, FactorGrantedAuthority.OTT_AUTHORITY)
				.build();
			security.authorizeHttpRequests(a -> a.requestMatchers("/admin").access(amf.authenticated()) //
			);//
		};
	}

}
