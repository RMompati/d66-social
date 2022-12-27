package com.eroldmr.d66.config;

import com.eroldmr.common.configs.smtp.SMTPEmailConfig;
import com.eroldmr.common.configs.smtp.SMTPEmailCredentials;
import com.eroldmr.d66.messaging.email.SMTPEmailCredentialsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 17:40
 */
@Configuration
@RequiredArgsConstructor
public class D66Config {

  private final SMTPEmailCredentials smtpEmailCredentials;

  @Bean
  SMTPEmailCredentials smtpCredentials() {
    return new SMTPEmailCredentialsImpl();
  }

  @Bean
  JavaMailSender javaMailSender() {
    return new SMTPEmailConfig(smtpEmailCredentials).javaMailSender();
  }
}
