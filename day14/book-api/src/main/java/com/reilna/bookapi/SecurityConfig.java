package com.reilna.bookapi;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserDetailsService appUserDetailsService;

    public SecurityConfig(AppUserDetailsService appUserDetailsService){
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/health/liveness",
                                "/actuator/health/readiness"
                        )
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/books", "/books/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/books")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/books/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .userDetailsService(appUserDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
