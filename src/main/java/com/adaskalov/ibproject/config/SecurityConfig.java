package com.adaskalov.ibproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration Beans.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * filters /medicine/**, /manufacturer/** and /account/** routes to ADMIN users, and /prescription/** to DOCTORs. Also enables X.509 authentication.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(
                (auth) -> {
                    try {
                        auth
                                .requestMatchers("/medicine/**", "/manufacturer/**", "/account/**").hasRole("ADMIN")
                                .requestMatchers("/prescription/**").hasRole("DOCTOR")
                                .anyRequest().authenticated()
                                .and()
                                .x509()
                                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                                .and()
                                .logout()
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return http.build();
    }
}
