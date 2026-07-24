package com.example.security.authorization.requests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;

import java.time.Duration;

// <.>
@EnableMultiFactorAuthentication(authorities = {})
@Configuration
class TimeboxedMfaConfiguration {

	@Bean
	Customizer<HttpSecurity> mfaCustomizer() {
		// <.>
		var mfaAmf = AuthorizationManagerFactories.multiFactor().requireFactors(objectBuilder -> {
			var duration = Duration.ofSeconds(30);
			var factors = new String[] { FactorGrantedAuthority.OTT_AUTHORITY,
					FactorGrantedAuthority.PASSWORD_AUTHORITY };
			for (var fga : factors) {
				// <.>
				objectBuilder.requireFactor(b -> b.authority(fga).validDuration(duration));
			}
		}).build();
		return security -> security.authorizeHttpRequests(a -> a.requestMatchers(ProfileController.ADMIN_PATH)
			.access(mfaAmf.hasRole("ADMIN"))
			.requestMatchers(ProfileController.USER_PATH)
			.authenticated());
	}

}
