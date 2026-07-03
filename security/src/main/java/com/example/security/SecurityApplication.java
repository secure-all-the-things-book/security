package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Map;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}

@Profile("globalmfa")
@Configuration
@EnableMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.OTT_AUTHORITY,
        FactorGrantedAuthority.PASSWORD_AUTHORITY
})
class GlobalMfaConfiguration {
}

@Configuration
@EnableMultiFactorAuthentication(authorities = {})
class MfaConfiguration {

    @Bean
    Customizer<HttpSecurity> httpSecurityCustomizer() {
        return security -> {
            var amf = AuthorizationManagerFactories.multiFactor()
                    .requireFactors(FactorGrantedAuthority.PASSWORD_AUTHORITY, FactorGrantedAuthority.OTT_AUTHORITY)
                    .build();
            security.authorizeHttpRequests(a -> a
                    .requestMatchers("/admin").access(amf.authenticated()) //
            );//
        };
    }
}

@Configuration
class PasswordConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

@Configuration
@Profile("memory")
class InMemoryConfiguration {

    @Bean
    InMemoryUserDetailsManager memoryUserDetailsManager(PasswordEncoder pw) {
        return new InMemoryUserDetailsManager(
                User.withUsername("user@anotherone.site").roles("ADMIN").password(pw.encode("p@ssw0rd")).build(),
                User.withUsername("josh@joshlong.com").roles("USER", "ADMIN").password(pw.encode("pw")).build()
        );
    }
}

@Configuration
class SecurityConfiguration {

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setEnableUpdatePassword(true);
        return userDetailsManager;
    }

}

@Controller
@ResponseBody
class AdminController {

    @GetMapping("/admin")
    Map<String, String> admin(Principal principal) {
        return Map.of("admin", principal.getName());
    }
}

@Controller
@ResponseBody
class MeController {

    @GetMapping("/me")
    Map<String, String> me(Principal principal) {
        return Map.of("name", principal.getName());
    }
}