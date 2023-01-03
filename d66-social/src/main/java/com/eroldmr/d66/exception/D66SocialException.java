package com.eroldmr.d66.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Mompati 'Patco' Keetile
 * @created 19-12-2022 @ 15:08
 */
@Getter
public class D66SocialException extends RuntimeException {
  private final HttpStatus status;
  public D66SocialException(String message) {
    this(message, BAD_REQUEST);
  }
  public D66SocialException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
