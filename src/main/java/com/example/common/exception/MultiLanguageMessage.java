package com.example.common.exception;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiLanguageMessage {

  private String zhMsg;

  private String enMsg;

  public String getMessage() {
    Locale locale = LocaleContextHolder.getLocale();
    String language = locale.getLanguage();
    if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(language)) {
      return zhMsg;
    }
    return enMsg;
  }
}
