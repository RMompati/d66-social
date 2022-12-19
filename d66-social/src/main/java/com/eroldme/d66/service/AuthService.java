package com.eroldme.d66.service;

import com.eroldme.d66.appuser.AppUser;
import com.eroldme.d66.appuser.AppUserRepository;
import com.eroldme.d66.appuser.register.dto.RegisterRequest;
import com.eroldme.d66.appuser.register.verificationtoken.VerificationToken;
import com.eroldme.d66.appuser.register.verificationtoken.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.eroldme.d66.utils.Utils.stringToken;

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

  @Transactional
  public void singUp(RegisterRequest registerRequest) {

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
    generateVerificationToken(appUser);
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
