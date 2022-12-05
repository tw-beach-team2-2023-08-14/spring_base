package com.example.common.exception;

import java.util.function.Supplier;

public class BusinessException extends RuntimeException implements Supplier<String> {
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
}
