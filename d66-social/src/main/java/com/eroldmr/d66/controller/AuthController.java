package com.eroldmr.d66.controller;

import com.eroldmr.d66.appuser.register.dto.RegisterRequest;
import com.eroldmr.d66.service.AuthService;
import com.eroldmr.d66.utils.D66Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 13:52
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/singup")
  public ResponseEntity<D66Response> registerUser(@RequestBody RegisterRequest registerRequest) {
    authService.registerUser(registerRequest);
    return ResponseEntity.ok(
        D66Response.respond()
            .message("User Registration Successful")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }
}
