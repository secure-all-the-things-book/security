package com.example.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.core.authority.FactorGrantedAuthority;

@Profile("globalmfa")
@Configuration
@EnableMultiFactorAuthentication(
		authorities = { FactorGrantedAuthority.OTT_AUTHORITY, FactorGrantedAuthority.PASSWORD_AUTHORITY })
class GlobalMfaConfiguration {

}
