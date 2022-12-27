package com.eroldmr.d66.utils;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 13:10
 */
@Builder(builderMethodName = "respond")
public class D66Response {
  private final String message;
  private final HttpStatus status;
  private final Integer statusCode;
}
