package com.example.domain.util;

import com.example.domain.entity.ProductDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class OrderUtil {
  private OrderUtil() {
    String exceptionMessage = "Class: " + OrderUtil.class + " should not be instantiated.";
    throw new UnsupportedOperationException(exceptionMessage);
  }

  public static String generateOrderId() {

    long MIN_ORDER_ID = 1000000000000000L;
    long MAX_ORDER_ID = 9999999999999999L;

    long currentTimeMillis = System.currentTimeMillis();
    Random random = new Random(currentTimeMillis);
    Long randomNumber = random.nextLong(MIN_ORDER_ID, MAX_ORDER_ID);

    return String.valueOf(randomNumber);
  }

  public static BigDecimal calculateTotalPrice(List<ProductDetail> productDetails) {
    return productDetails.stream()
        .map(
            productDetail ->
                productDetail.getPrice().multiply(BigDecimal.valueOf(productDetail.getAmount())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
