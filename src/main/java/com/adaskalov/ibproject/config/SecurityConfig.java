package com.adaskalov.ibproject.config;

import com.adaskalov.ibproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final UserService userService;

//    public SecurityConfig(UserService userService) {
//        this.userService = userService;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(
                (auth) -> {
                    try {
                        auth
                                .requestMatchers("/medicine/**","/manufacturer/**","/account/**").hasRole("ADMIN")
                                .requestMatchers("/prescription/**").hasRole("DOCTOR")
                                .anyRequest().authenticated()
                                .and()
//                                .formLogin().permitAll()
//                                .defaultSuccessUrl("/medicine")
//                                .and()
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

//    @Bean
//    public UserService userDetailsService() {return userService;}
}
