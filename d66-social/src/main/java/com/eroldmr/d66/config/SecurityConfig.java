package com.eroldmr.d66.config;

import com.eroldmr.d66.appuser.AppUserService;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 13:52
 */
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final AppUserService appUserService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  @SneakyThrows(Exception.class)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    return http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .authorizeRequests()
            .antMatchers("/api/auth/**")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/subreddit")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .authenticationProvider(daoAuthenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(appUserService);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(List.of(daoAuthenticationProvider()));
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      response.setContentType("application/json");
      response.setStatus(UNAUTHORIZED.value());
      D66Response response1 = D66Response
              .respond()
              .statusCode(UNAUTHORIZED.value())
              .status(UNAUTHORIZED)
              .message("User authentication failed.")
              .build();

      ObjectMapper objectMapper = new ObjectMapper();

      response.getWriter().println(objectMapper.writer(new DefaultPrettyPrinter()).writeValueAsString(response1));

    };
  }
}
