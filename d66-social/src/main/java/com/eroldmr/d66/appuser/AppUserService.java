package com.eroldmr.d66.appuser;

import com.eroldmr.common.configs.smtp.SMTPEmailInfo;
import com.eroldmr.common.configs.smtp.SMTPEmailService;
import com.eroldmr.d66.appuser.verificationtoken.VerificationToken;
import com.eroldmr.d66.appuser.verificationtoken.VerificationTokenRepository;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.messaging.email.MailContentBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.eroldmr.d66.utils.Utils.stringToken;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 11:20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

  private final AppUserRepository appUserRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final SMTPEmailService smtpEmailService;
  private final MailContentBuilder mailContentBuilder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return appUserRepository.findAppUserByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                    format("User with email %s not found.", username)
            ));
  }

  @Transactional
  public void registerUser(AppUser appUser0) {
    log.info("Saving new user.");

    userExists(appUser0);

    AppUser appUser = appUserRepository.save(appUser0);

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
  public void activateUserAccount(String token) {
    log.info("Activating user account.");
    VerificationToken vToken = verificationTokenRepository.findByToken(token)
            .orElseThrow(() -> new D66SocialException("Invalid token."));

    enableUserAccount(vToken);
  }

  @Transactional(noRollbackFor = {D66SocialException.class})
  private void enableUserAccount(VerificationToken vToken) {
    log.info("Account activation in progress.");

    AppUser appUser = vToken.getAppUser();

    accountEnabled(appUser);

    if (!now().isBefore(vToken.getExpiresAt())) {
      log.info("Account activation unsuccessful.");

      generateVerificationToken(appUser);
      verificationTokenRepository.delete(vToken);

      throw new D66SocialException("Account Activation unsuccessful. A new activation link has been sent.");
    }

    appUserRepository.enableUserByEmail(appUser.getEmail());
    log.info("Account activation successful.");
  }

  private void accountEnabled(AppUser appUser) {
    log.info("Checking if account is already enabled.");
    if (appUser.getEnabled()) {
      throw new D66SocialException("Account already activated, please proceed to login.", ALREADY_REPORTED);
    }
  }

  private void userExists(AppUser appUser) {
    log.info("Checking if the user details exists.");
    Optional<AppUser> user = appUserRepository.findAppUserByEmail(appUser.getEmail());
    boolean exists = user.isPresent();
    boolean enabled = exists && user.get().getEnabled();

    if (enabled) {
      throw new D66SocialException("Email is taken, please proceed to login or use a different email.");
    } else if (exists) {
      throw new D66SocialException("Email is taken and account Activation link has been sent  or use a different email.");
    }

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
