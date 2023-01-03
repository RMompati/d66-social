package com.eroldmr.d66.api;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserService;
import com.eroldmr.d66.appuser.register.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:02
 */
@Service
@RequiredArgsConstructor
public class ApiService {

  private final AppUserService appUserService;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void registerUser(RegisterRequest registerRequest) {
    appUserService.registerUser(
            AppUser
                    .NewUser()
                      .firstName(registerRequest.getFirstName())
                      .lastName(registerRequest.getLastName())
                      .email(registerRequest.getEmail())
                      .password(passwordEncoder.encode(registerRequest.getPassword()))
                      .createdAt(now())
                      .enabled(false)
                      .locked(false)
                    .build()
    );
  }

  @Transactional
  public void activateUserAccount(String token) {
    appUserService.activateUserAccount(token);
  }
}
