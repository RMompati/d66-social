package com.eroldmr.d66.appuser.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 12:09
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {
  private String email;
  private String firstName;
  private String lastName;
  private String password;
}
