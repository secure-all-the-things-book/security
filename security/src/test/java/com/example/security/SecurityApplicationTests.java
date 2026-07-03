package com.example.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@SpringBootTest
class SecurityApplicationTests {

	@Test
	void authentication() throws Exception {

		// <.>
		var pw = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		// <.>
		var josh = User.withUsername("josh").password(pw.encode("pw")).roles("USER").build();
		var rob = User.withUsername("rob").password(pw.encode("pw")).roles("USER", "ADMIN").build();
		var users = List.of(josh, rob);
		var userDetailsService = new InMemoryUserDetailsManager(users);

		// <.>
		var daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		daoAuthenticationProvider.afterPropertiesSet();

		// <.>
		var authenticationProviders = List.of((AuthenticationProvider) daoAuthenticationProvider);
		var authenticationManager = new ProviderManager(authenticationProviders);
		authenticationManager.afterPropertiesSet();

		// <.>
		var authentication = new UsernamePasswordAuthenticationToken("josh", "pw");
		Assertions.assertFalse(authentication.isAuthenticated(), "the user is not authenticated");
		var authenticated = authenticationManager.authenticate(authentication);
		Assertions.assertTrue(authenticated.isAuthenticated(), "the user is authenticated");
	}

}
