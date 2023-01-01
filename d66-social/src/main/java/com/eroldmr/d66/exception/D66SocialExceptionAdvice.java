package com.eroldmr.d66.exception;

import com.eroldmr.d66.utils.D66Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
            .message(d66SocialException.getMessage())
            .statusCode(BAD_REQUEST.value())
            .status(BAD_REQUEST)
            .build()
    );
  }
}
