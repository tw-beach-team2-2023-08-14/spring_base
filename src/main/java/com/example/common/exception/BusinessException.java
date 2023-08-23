package com.example.common.exception;

import java.util.function.Supplier;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;

@Getter
public class BusinessException extends RuntimeException implements Supplier<String>, ErrorResponse {
  private final IExceptionCode code;

  private ILangMessage customizeLangMessage;

  private final String message;

  public BusinessException(IExceptionCode code, String message) {
    this.code = code;
    this.message = message;
  }

  public BusinessException(IExceptionCode code, ILangMessage customizeLangMessage, String message) {
    this.code = code;
    this.customizeLangMessage = customizeLangMessage;
    this.message = message;
  }

  @Override
  public String get() {
    return null;
  }

  @Override
  @NonNull
  public HttpStatusCode getStatusCode() {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    if (code instanceof BaseExceptionCode baseExceptionCode) {
      status = baseExceptionCode.code;
    }
    return status;
  }

  @Override
  @NonNull
  public ProblemDetail getBody() {
    return ProblemDetail.forStatusAndDetail(getStatusCode(), message);
  }
}
