package com.eroldme.d66.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 13:52
 */
@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;

  @Bean
  @SneakyThrows(Exception.class)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
            .csrf().disable()
            .authorizeRequests()
              .antMatchers("/api/auth/**")
              .permitAll()
            .anyRequest()
              .authenticated()
            .and()
            .authenticationProvider(daoAuthenticationProvider());
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
//    provider.setUserDetailsService(userDetailsService); TODO: Set UserDetailsService.
    return provider;
  }
}
