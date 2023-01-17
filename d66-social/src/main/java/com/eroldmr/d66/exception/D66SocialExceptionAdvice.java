package com.eroldmr.d66.exception;

import com.eroldmr.d66.response.D66Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Mompati 'Patco' Keetile
 * @created 02-01-2023 @ 00:09
 */
@ControllerAdvice
public class D66SocialExceptionAdvice {
  @ExceptionHandler(D66SocialException.class)
  public ResponseEntity<D66Response> handleD66Exception(D66SocialException d66SocialException) {
    return ResponseEntity
            .status(d66SocialException.getStatus())
            .body(
                    D66Response
                            .respond()
                            .statusCode(d66SocialException.getStatus().value())
                            .status(d66SocialException.getStatus())
                            .message(d66SocialException.getMessage())
                            .build()
            );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<D66Response> handleSQLException(DataIntegrityViolationException psqlException) {
    return ResponseEntity
            .status(BAD_REQUEST)
            .body (
                    D66Response
                            .respond()
                            .statusCode(FORBIDDEN.value())
                            .status(FORBIDDEN)
                            .message("User registration failed.\nTry using a different username or email.")
                            .build()
            );
  }
}
