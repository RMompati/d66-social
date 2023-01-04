package com.eroldmr.d66.security;

import com.eroldmr.d66.appuser.AppUser;
import com.eroldmr.d66.exception.D66SocialException;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 12:12
 */
@Service
public class JwtProvider {

  private KeyStore keyStore;

  @PostConstruct
  public void init() {

    try {
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceASStream = getClass().getResourceAsStream("/springblog.jks");
      keyStore.load(resourceASStream, "secret".toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException exc) {
      throw new D66SocialException("Error while loading keystore.");
    }
  }

  public String generateJWToken(Authentication authentication) {
    AppUser appUser = (AppUser) authentication.getPrincipal();
    return Jwts
            .builder()
            .setSubject(appUser.getUsername())
            .signWith(privateKey())
            .compact();
  }

  private PrivateKey privateKey() {
    try {
      return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException exc) {
      throw new D66SocialException("Error while retrieving public key from keystore");
    }
  }

  public boolean validateToken(String jwToken) {
    headerClaimsJws(jwToken);

    return true;
  }

  public String getUsernameFromJwt(String jwToken) {
    return headerClaimsJws(jwToken)
            .getBody()
            .getSubject();
  }

  private Jws<Claims> headerClaimsJws(String jwToken) {
    return parserBuilder()
            .setSigningKey(publicKey())
            .build()
            .parseClaimsJws(jwToken);
  }

  private PublicKey publicKey() {
    try {
      return keyStore.getCertificate("springblog").getPublicKey();
    } catch (KeyStoreException exc) {
      throw new D66SocialException("Error while retrieving public key from keystore");
    }
  }
}
