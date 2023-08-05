package com.example.common.exception;

public enum ExceptionCode implements IExceptionCode {
  NOT_FOUND("not found");

  String enMsg;

  ExceptionCode() {}

  ExceptionCode(String enMsg) {
    this.enMsg = enMsg;
  }

  @Override
  public String getValue() {
    return this.name();
  }

  @Override
  public String getEnMsg() {
    return enMsg;
  }
}
