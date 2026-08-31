package com.example.security.authorization.requests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.List;
import java.util.Objects;

@Configuration
class CmsControllerConfiguration {

	@Bean
	Customizer<HttpSecurity> cmsHttpSecurityCustomizer() {
		return http -> http.authorizeHttpRequests(auth -> auth
			// <.>
			.requestMatchers(HttpMethod.POST, "/api/**")
			.hasRole("EDITOR")
			// <.>
			.requestMatchers("/api/admin/**")
			.hasRole("ADMIN")
			// <.>
			.requestMatchers("/api/files/file.{fileId}")
			.access(//
					(supplier, authorizationContext) -> {
						var authentication = supplier.get();
						var isAdmin = Objects.requireNonNull(authentication)
							.getAuthorities()//
							.stream() //
							.anyMatch(ga -> Objects.requireNonNull(ga.getAuthority()).equals("ROLE_ADMIN"));
						var variables = Objects.requireNonNull(authorizationContext).getVariables();
						if (!variables.isEmpty()) {
							var fileId = Long.parseLong(variables.getOrDefault("fileId", "0"));
							IO.println("the file id is " + fileId);
							return new AuthorizationDecision(isAdmin && canAccess(fileId));
						}
						return new AuthorizationDecision(false);
					})
			// <.>
			.requestMatchers("/api/**")
			.hasRole("USER"));
	}

	private boolean canAccess(long fileId) {
		var goodFileIds = List.of(1L, 2L, 3L);
		return fileId > 0 && goodFileIds.contains(fileId);
	}

}
