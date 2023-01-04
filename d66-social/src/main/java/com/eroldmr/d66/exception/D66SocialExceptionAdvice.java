package com.eroldmr.d66.exception;

import com.eroldmr.d66.utils.D66Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Mompati 'Patco' Keetile
 * @created 02-01-2023 @ 00:09
 */
@ControllerAdvice
public class D66SocialExceptionAdvice {
  @ExceptionHandler(D66SocialException.class)
  public ResponseEntity<D66Response> handleD66Exception(D66SocialException d66SocialException) {
    return ResponseEntity.ok(
        D66Response
            .respond()
                .statusCode(d66SocialException.getStatus().value())
                .status(d66SocialException.getStatus())
                .message(d66SocialException.getMessage())
            .build()
    );
  }
}
