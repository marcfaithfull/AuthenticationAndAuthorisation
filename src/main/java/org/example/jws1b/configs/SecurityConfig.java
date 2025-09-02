package org.example.jws1b.configs;

import org.example.jws1b.utils.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth-> auth
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/v1/getname").hasRole("admin")
                                .anyRequest().authenticated())
                .csrf(csrf->csrf
                        .ignoringRequestMatchers("/api/v1/**")
                        .ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame
                        .sameOrigin()))
                .oauth2ResourceServer(oauth->oauth
                        .jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        return http.build();
    }
}
