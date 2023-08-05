package com.example.common.exception;

public interface ILangMessage {

  String getEnMsg();

  default Object[] getParams() {
    return new Object[0];
  }

  default void setParams(Object... params) {}

  default String getLangMessage() {
    return MultiLanguageMessage.builder()
        .enMsg(String.format(getEnMsg(), getParams()))
        .build()
        .getMessage();
  }
}
