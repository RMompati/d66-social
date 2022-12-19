package com.eroldme.d66.user.register.dto;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 10:10
 */
data class RegisterRequest(
  val email: String,
  val username: String,
  val password: String
)
