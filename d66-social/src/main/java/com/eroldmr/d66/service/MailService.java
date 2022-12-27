package com.eroldmr.d66.service;

import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.messaging.email.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:55
 */
@AllArgsConstructor
@Service
@Slf4j
public class MailService {

  private final JavaMailSender mailSender;
  private final MailContentBuilder mailContentBuilder;

  public void sendMail(NotificationEmail notificationEmail) {
    MimeMessagePreparator messagePreparator = (mimeMessage) -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom("mompati.keetile@gmail.com");
      messageHelper.setTo(notificationEmail.getRecipient());
      messageHelper.setSubject(notificationEmail.getSubject());
      mimeMessage.setText(mailContentBuilder.build(notificationEmail.getBody()));
    };

    try {
      mailSender.send(messagePreparator);
      log.info("Activation email sent.");
    } catch (MailException e) {
      log.error(e.getMessage());
      throw new D66SocialException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
    }
  }
}
