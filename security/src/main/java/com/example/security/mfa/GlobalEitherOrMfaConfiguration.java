package com.example.security.mfa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AllRequiredFactorsAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagerFactory;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;
import org.springframework.security.authorization.RequiredFactor;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;

@Configuration
@EnableMultiFactorAuthentication(authorities = {})
class GlobalEitherOrMfaConfiguration {

    @Bean
    AuthorizationManagerFactory<Object> authorizationManagerFactory() {
        // <.>
        var webauthn = AllRequiredFactorsAuthorizationManager
                .builder()
                .requireFactor(RequiredFactor.Builder::webauthnAuthority)
                .build();

        //<.>
        var passwordAndOtt = AllRequiredFactorsAuthorizationManager
                .builder()
                .requireFactor(RequiredFactor.Builder::passwordAuthority)
                .requireFactor(RequiredFactor.Builder::ottAuthority)
                .build();

        var mfa = new DefaultAuthorizationManagerFactory<>();
        mfa.setAdditionalAuthorization(AllRequiredFactorsAuthorizationManager.anyOf(webauthn, passwordAndOtt));
        return mfa;
    }

}
