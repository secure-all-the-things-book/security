package com.example.security.webauthn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
class WebAuthnConfiguration {

    @Bean
    Customizer<HttpSecurity> webAuthnCustomizer() {
        return security -> security //
                .webAuthn(c -> c //
                        .allowedOrigins("http://localhost:8080")//
                        .rpId("localhost")// <.>
                        .rpName("bootifulpasskeys")// <.>
                );
    }
}
