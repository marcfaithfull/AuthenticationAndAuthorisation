package org.example.jws1b.configs;

import org.example.jws1b.utils.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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

                        .requestMatchers("/api/v2/deletepost/**").hasAnyRole("user", "admin")

                        .requestMatchers("/api/v2/count").hasRole("admin")

                        .requestMatchers("/api/v2/posts").hasRole("user")
                        .requestMatchers("/api/v2/post/{id}").hasRole("user")
                        .requestMatchers("/api/v2/newpost").hasRole("user")

                        .requestMatchers("/api/v2/**").permitAll()
                        .anyRequest().authenticated())

                .csrf(csrf->csrf.disable())
                        //.ignoringRequestMatchers("/api/v1/**")
                        //.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame->frame
                        .sameOrigin()))

                .oauth2ResourceServer(oauth->oauth
                        .jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)));

        return http.build();
    }
}
