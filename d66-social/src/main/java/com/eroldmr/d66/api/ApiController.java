package com.eroldmr.d66.api;

import com.eroldmr.d66.appuser.register.dto.RegisterRequest;
import com.eroldmr.d66.utils.D66Response;
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
public class ApiController {

  private final ApiService apiService;

  @PostMapping("/singup")
  public ResponseEntity<D66Response> registerUser(@RequestBody RegisterRequest registerRequest) {
    apiService.registerUser(registerRequest);
    return ResponseEntity.ok(
        D66Response.respond()
            .message("User Registration Successful")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }

  @GetMapping(value = "activate-account/{token}")
  public ResponseEntity<D66Response> activateAccount(@PathVariable String token) {
    apiService.activateUserAccount(token);

    return ResponseEntity.ok(
        D66Response.respond()
            .message( "Account Activated Successfully.")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }
}
