package com.eroldmr.d66.messaging.email;

import com.eroldmr.common.configs.smtp.SMPTEmailSender;
import com.eroldmr.common.configs.smtp.SMTPEmailCredentials;
import com.eroldmr.common.configs.smtp.SMTPEmailInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 17:26
 */
@Service
public class SMTPEmailService extends SMPTEmailSender {
  @Autowired
  public SMTPEmailService(JavaMailSender mailSender, SMTPEmailCredentials smtpEmailCredentials) {
    super(mailSender, smtpEmailCredentials);
  }
}
