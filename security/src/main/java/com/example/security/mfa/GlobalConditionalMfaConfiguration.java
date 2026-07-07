package com.example.security.mfa;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.authorization.MultiFactorCondition;
import org.springframework.security.core.authority.FactorGrantedAuthority;

@Profile("globalmfa")
@Configuration
// <.>
@EnableMultiFactorAuthentication(
        authorities = {FactorGrantedAuthority.WEBAUTHN_AUTHORITY, FactorGrantedAuthority.PASSWORD_AUTHORITY},
        when = {MultiFactorCondition.WEBAUTHN_REGISTERED}
)
class GlobalConditionalMfaConfiguration {
}
