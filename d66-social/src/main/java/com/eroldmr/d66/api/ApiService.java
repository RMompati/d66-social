package com.eroldmr.d66.api;

import com.eroldmr.common.configs.smtp.SMTPEmailInfo;
import com.eroldmr.common.configs.smtp.SMTPEmailService;
import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserRepository;
import com.eroldmr.d66.appuser.register.dto.RegisterRequest;
import com.eroldmr.d66.appuser.register.verificationtoken.VerificationToken;
import com.eroldmr.d66.appuser.register.verificationtoken.VerificationTokenRepository;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.messaging.email.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.eroldmr.d66.utils.Utils.stringToken;
import static java.time.LocalDateTime.now;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:02
 */
@AllArgsConstructor
@Service
@Slf4j
public class ApiService {

  private final PasswordEncoder passwordEncoder;
  private final AppUserRepository appUserRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final SMTPEmailService smtpEmailService;
  private final MailContentBuilder mailContentBuilder;

  @Transactional
  public void registerUser(RegisterRequest registerRequest) {
    log.info("Saving new user.");

    AppUser appUser = appUserRepository.save(
            AppUser
                    .NewUser()
                      .username(registerRequest.getUsername())
                      .email(registerRequest.getEmail())
                      .password(passwordEncoder.encode(registerRequest.getPassword()))
                      .createdAt(now())
                      .enabled(false)
                    .build()
    );

    log.info("Generating user verification token.");

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

  @Transactional
  public String activateUserAccount(String token) {
    log.info("Activating user account.");
    VerificationToken vToken = verificationTokenRepository.findByToken(token)
        .orElseThrow(() -> new D66SocialException("Invalid token."));

    return enableUserAccount(vToken);
  }

  @Transactional
  private String enableUserAccount(VerificationToken vToken) {
    log.info("Account activation in progress.");
    log.info("Token expired ? " + vToken.getExpiresAt().isBefore(now()));
    if (now().isBefore(vToken.getExpiresAt())) {
      appUserRepository.enableUserByEmail(vToken.getAppUser().getEmail());
      log.info("Account activation successful.");
      return "success";
    }

    log.info("Account activation unsuccessful.");

    generateVerificationToken(vToken.getAppUser());
    verificationTokenRepository.delete(vToken);
    return "failure";
  }

  @Transactional
  private String generateVerificationToken(AppUser appUser) {
    log.info("Saving verification token.");
    LocalDateTime expiresAt = now().plusMinutes(15);
    log.info("Verification token expires at: " + expiresAt);
    return verificationTokenRepository.save(
            VerificationToken
                    .NewToken()
                      .token(stringToken())
                      .expiresAt(expiresAt)
                      .appUser(appUser)
                    .build()
    ).getToken();
  }
}
