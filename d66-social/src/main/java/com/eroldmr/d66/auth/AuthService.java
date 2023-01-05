package com.eroldmr.d66.auth;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserService;
import com.eroldmr.d66.appuser.dto.LoginRequest;
import com.eroldmr.d66.appuser.dto.RegisterRequest;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 14:02
 */
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AppUserService appUserService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

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

  public D66Response login(LoginRequest loginRequest) {
    Authentication authenticate;

    try {
      authenticate = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
      );
    } catch (AuthenticationException exc) {
      throw new D66SocialException("User authentication failed.", UNAUTHORIZED);
    }

    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String jwToken = jwtProvider.generateJWToken(authenticate);

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Login successful.")
            .username(loginRequest.getUsername())
            .data(of("auth", jwToken))
            .build();
  }
}
