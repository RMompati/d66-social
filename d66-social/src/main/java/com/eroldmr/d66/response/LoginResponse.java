package com.eroldmr.d66.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Mompati 'Patco' Keetile
 */
@Data
@SuperBuilder(builderMethodName = "login")
@JsonInclude(NON_NULL)
public class LoginResponse {
  private String username;
  private String authenticationToken;
  private String refreshToken;
  private Instant expiresAt;
}
