package com.example.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseExceptionCode implements IExceptionCode {
  NOT_FOUND_CUSTOMER("Not found customer."),
  NOT_FOUND_PRODUCT("Not found product."),
  INVALID_PRODUCT("Invalid product."),
  INSUFFICIENT_PRODUCT("Insufficient product."),
  NOT_FOUND_ORDER("Not found order."),

  NON_EXIST_ORDER("Order does not exist.", HttpStatus.NOT_FOUND),

  INVALID_ORDER_STATUS("Order already in cancelled stage.", HttpStatus.CONFLICT),

  INVALID_CONSUMER_ID("Customer id not match.", HttpStatus.CONFLICT);

  BaseExceptionCode(String enMsg) {
    this.enMsg = enMsg;
  }

  BaseExceptionCode(String enMsg, HttpStatus code) {
    this.enMsg = enMsg;
    this.code = code;
  }

  final String enMsg;
  HttpStatus code;

  @Override
  public String getValue() {
    return this.name();
  }

  @Override
  public String getLangMessage() {
    return IExceptionCode.super.getLangMessage();
  }
}
