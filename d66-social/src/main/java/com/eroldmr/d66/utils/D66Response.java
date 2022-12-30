package com.eroldmr.d66.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Mompati 'Patco' Keetile
 * @created 27-12-2022 @ 13:10
 */
@Data
@SuperBuilder(builderMethodName = "respond")
@JsonInclude(NON_NULL)
public class D66Response {
  protected LocalDateTime timestamp;
  protected Integer statusCode;
  protected HttpStatus status;
  protected String reason;
  protected String message;
  protected Map<?, ?> data;
}
