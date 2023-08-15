package com.example.domain.util;

import com.example.domain.entity.ProductDetail;
import java.math.BigDecimal;
import java.util.List;

public class OrderUtil {
  private OrderUtil() {
    String exceptionMessage = "Class: " + OrderUtil.class + " should not be instantiated.";
    throw new UnsupportedOperationException(exceptionMessage);
  }

  public static BigDecimal calculateTotalPrice(List<ProductDetail> productDetails) {
    return productDetails.stream()
        .map(
            productDetail ->
                productDetail.getPrice().multiply(BigDecimal.valueOf(productDetail.getAmount())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
