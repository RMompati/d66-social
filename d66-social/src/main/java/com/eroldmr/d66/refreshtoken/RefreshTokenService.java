package com.eroldmr.d66.refreshtoken;

import com.eroldmr.d66.exception.D66SocialException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eroldmr.d66.utils.Utils.stringToken;
import static java.time.Instant.now;

/**
 * @author Mompati 'Patco' Keetile
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public RefreshToken generateRefreshToken() {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(stringToken());
    refreshToken.setCreatedAt(now());

    return refreshTokenRepository.save(refreshToken);
  }

  @Transactional
  public RefreshToken validateToken(String token) {
    return refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new D66SocialException("Invalid refresh token"));
  }

  @Transactional
  public void delete(String token) {
    refreshTokenRepository.deleteByToken(token);
  }
}
