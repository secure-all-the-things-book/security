package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@Profile("memory")
class InMemoryConfiguration {

    @Bean
    InMemoryUserDetailsManager memoryUserDetailsManager(PasswordEncoder pw) {
        return new InMemoryUserDetailsManager(
                User.withUsername("user@anotherone.site").roles("ADMIN").password(pw.encode("p@ssw0rd")).build(),
                User.withUsername("josh@joshlong.com").roles("USER", "ADMIN").password(pw.encode("pw")).build());
    }

}
