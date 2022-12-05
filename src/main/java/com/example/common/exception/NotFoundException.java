package com.example.common.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
  private final IExceptionCode code;

  private ILangMessage customizeLangMessage;

  private final String message;

  private NotFoundException(IExceptionCode code, String message) {
    this.code = code;
    this.message = message;
  }

  public NotFoundException(IExceptionCode code, ILangMessage customizeLangMessage, String message) {
    this.code = code;
    this.customizeLangMessage = customizeLangMessage;
    this.message = message;
  }

  public static Supplier<NotFoundException> notFoundException(ILangMessage customizeLangMessage,
      String... messages) {
    String message = StringUtils.join(messages, " ");
    return () -> new NotFoundException(ExceptionCode.NOT_FOUND, customizeLangMessage, message);
  }
}
