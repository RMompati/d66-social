package com.eroldmr.d66.appuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 03-01-2023 @ 11:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
  private String username;
  private String password;
}
