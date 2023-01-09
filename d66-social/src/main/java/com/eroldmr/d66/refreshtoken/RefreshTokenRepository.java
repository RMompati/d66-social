package com.eroldmr.d66.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mompati 'Patco' Keetile
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByToken(String token);
}
