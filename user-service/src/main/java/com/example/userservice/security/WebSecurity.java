package com.example.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/health_check").permitAll()
                        .requestMatchers("/user-service/health_check").permitAll()

                        .anyRequest().authenticated()
        );
        http.headers(header -> header.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }
}
