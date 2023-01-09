package com.eroldmr.d66.refreshtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Mompati 'Patco' Keetile
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto {
  @NotBlank
  private String refreshToken;
  private String username;
}
