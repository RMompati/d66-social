package com.eroldmr.d66.auth;

import com.eroldmr.d66.appuser.dto.LoginRequest;
import com.eroldmr.d66.appuser.dto.RegisterRequest;
import com.eroldmr.d66.response.D66Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/signup")
  public ResponseEntity<D66Response> registerUser(@RequestBody RegisterRequest registerRequest) {
    authService.registerUser(registerRequest);
    return ResponseEntity.ok(
        D66Response
                .respond()
                  .statusCode(OK.value())
                  .status(OK)
                  .message("User Registration Successful")
                .build()
    );
  }

  @GetMapping(value = "activate-account/{token}")
  public ResponseEntity<D66Response> activateAccount(@PathVariable String token) {
    authService.activateUserAccount(token);

    return ResponseEntity.ok(
        D66Response
                .respond()
                  .statusCode(OK.value())
                  .status(OK)
                  .message("Account Activated Successfully.")
                .build()
    );
  }

  @PostMapping("/login")
  public ResponseEntity<D66Response> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }
}
