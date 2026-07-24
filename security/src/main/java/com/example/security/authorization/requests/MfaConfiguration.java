package com.example.security.authorization.requests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;

// <.>
@Configuration
@EnableMultiFactorAuthentication(authorities = {})
class MfaConfiguration {

	@Bean
	Customizer<HttpSecurity> mfaCustomizer() {
		// <.>
		var mfaAmf = AuthorizationManagerFactories.multiFactor()
			.requireFactors(FactorGrantedAuthority.PASSWORD_AUTHORITY, FactorGrantedAuthority.OTT_AUTHORITY)
			.build();
		return security -> security.authorizeHttpRequests(a -> a
			// <.>
			.requestMatchers(ProfileController.ADMIN_PATH)
			.access(mfaAmf.hasRole("ADMIN"))
			.requestMatchers(ProfileController.USER_PATH)
			.authenticated());
	}

}
