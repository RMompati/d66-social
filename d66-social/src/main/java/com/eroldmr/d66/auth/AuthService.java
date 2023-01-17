package com.eroldmr.d66.auth;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.appuser.AppUserService;
import com.eroldmr.d66.appuser.dto.LoginRequest;
import com.eroldmr.d66.appuser.dto.RegisterRequest;
import com.eroldmr.d66.refreshtoken.RefreshToken;
import com.eroldmr.d66.refreshtoken.RefreshTokenRepository;
import com.eroldmr.d66.refreshtoken.RefreshTokenService;
import com.eroldmr.d66.refreshtoken.dto.RefreshTokenDto;
import com.eroldmr.d66.exception.D66SocialException;
import com.eroldmr.d66.response.D66Response;
import com.eroldmr.d66.response.LoginResponse;
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

import java.time.Instant;

import static java.time.Instant.now;
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

  private final RefreshTokenService refreshTokenService;
  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public void registerUser(RegisterRequest registerRequest) {
    appUserService.registerUser(
            AppUser
                    .NewUser()
                    .locked(false)
                    .enabled(false)
                    .createdAt(now())
                    .email(registerRequest.getEmail())
                    .lastName(registerRequest.getLastName())
                    .username(registerRequest.getUsername())
                    .firstName(registerRequest.getFirstName())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
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
      throw new D66SocialException("Invalid username or password.", UNAUTHORIZED);
    }

    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String jwToken = jwtProvider.generateJWToken(authenticate);
    Instant expiresAt = now().plusMillis(jwtProvider.getJwtExpirationInMillis());

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Login successful.")
            .username(loginRequest.getUsername())
            .data(of(
                    "auth",
                    LoginResponse
                            .login()
                            .authenticationToken(jwToken)
                            .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                            .expiresAt(expiresAt)
                            .username(loginRequest.getUsername())
                            .build()
            ))
            .build();
  }

  public D66Response refreshToken(RefreshTokenDto refreshTokenDto) {
    refreshTokenService.validateToken(refreshTokenDto.getRefreshToken());
    String jwToken = jwtProvider.generateTokenWithUsername(refreshTokenDto.getUsername());
    Instant expiresAt = now().plusMillis(jwtProvider.getJwtExpirationInMillis());

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("Auth token refresh.")
            .username(refreshTokenDto.getUsername())
            .data(of(
                    "auth",
                    LoginResponse
                            .login()
                            .authenticationToken(jwToken)
                            .refreshToken(refreshTokenDto.getRefreshToken())
                            .expiresAt(expiresAt)
                            .username(refreshTokenDto.getUsername())
                            .build()
            ))
            .build();
  }

  @Transactional
  public D66Response logout(RefreshTokenDto refreshTokenDto) {

    RefreshToken refreshToken = refreshTokenService.validateToken(refreshTokenDto.getRefreshToken());
    refreshTokenRepository.deleteByToken(refreshToken.getToken());

    return D66Response
            .respond()
            .timestamp(now())
            .statusCode(OK.value())
            .status(OK)
            .message("You've been logged out.")
            .build();
  }
}
