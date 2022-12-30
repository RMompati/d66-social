package com.eroldmr.d66.config;

import com.eroldmr.common.configs.smtp.SMTPEmailCredentials;
import com.eroldmr.d66.messaging.email.SMTPEmailCredentialsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 17:40
 */
@Configuration
public class D66Config {


  @Bean
  SMTPEmailCredentials smtpCredentials() {
    return new SMTPEmailCredentialsImpl();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
