package com.enterprise.platform.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //Disable CSRF
                .csrf(csrf -> csrf.disable())

                //Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/test",
                                "/actuator/health"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                //use basic auth
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
