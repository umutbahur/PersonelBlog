package com.example.PersonalBlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfiguration {
    
    @Bean
    SecurityFilterChain defauSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req.
                requestMatchers("/dashboard", "/article/add", "/article/update/**", "article/delete").hasRole("ADMIN")
                .requestMatchers("/article/like", "/article/comment").hasAnyRole("ADMIN", "USER")
                .anyRequest().permitAll());
        http.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login/process").permitAll());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
