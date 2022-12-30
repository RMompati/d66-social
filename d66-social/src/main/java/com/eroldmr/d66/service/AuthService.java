package com.eroldmr.d66.service;

import com.eroldmr.common.configs.smtp.SMTPEmailInfo;
import com.eroldmr.common.configs.smtp.SMTPEmailService;
import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserRepository;
import com.eroldmr.d66.appuser.register.dto.RegisterRequest;
import com.eroldmr.d66.appuser.register.verificationtoken.VerificationToken;
import com.eroldmr.d66.appuser.register.verificationtoken.VerificationTokenRepository;
import com.eroldmr.d66.messaging.email.MailContentBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.eroldmr.d66.utils.Utils.stringToken;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:02
 */
@AllArgsConstructor
@Service
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AppUserRepository appUserRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final SMTPEmailService smtpEmailService;
  private final MailContentBuilder mailContentBuilder;

  @Transactional
  public void registerUser(RegisterRequest registerRequest) {

    AppUser appUser = appUserRepository.save(
            AppUser
                    .NewUser()
                      .username(registerRequest.getUsername())
                      .email(registerRequest.getEmail())
                      .password(passwordEncoder.encode(registerRequest.getPassword()))
                      .createdAt(Instant.now())
                      .enabled(false)
                    .build()
    );

    String token = generateVerificationToken(appUser);
    String link = String.format("http://localhost:8080/api/auth/activate-account/%s", token);

    smtpEmailService.sendEmail(
        SMTPEmailInfo
            .builder()
              .recipient(appUser.getEmail())
              .subject("D66 Social - Account Activation")
              .body(mailContentBuilder.build(appUser.getUsername(), link))
              .html(true)
            .build()
    );
  }

  private String generateVerificationToken(AppUser appUser) {
    return verificationTokenRepository.save(
            VerificationToken
                    .NewToken()
                      .token(stringToken())
                      .appUser(appUser)
                    .build()
    ).getToken();
  }
}
