package com.example.common.exception;

import lombok.Getter;

@Getter
public enum BaseExceptionCode implements IExceptionCode {
  NOT_FOUND_CUSTOMER("Not found customer."),
  NOT_FOUND_PRODUCT("Not found product."),
  INVALID_PRODUCT("Invalid product."),
  INSUFFICIENT_PRODUCT("Insufficient product."),

  NON_EXIST_ORDER("Order does not exist."),

  INVALID_ORDER_STATUS("Order already in cancelled stage."),

  CONFLICT("Order already in cancelled stage.");

  BaseExceptionCode(String enMsg) {
    this.enMsg = enMsg;
  }

  String enMsg;

  @Override
  public String getValue() {
    return this.name();
  }

  @Override
  public String getLangMessage() {
    return IExceptionCode.super.getLangMessage();
  }
}
