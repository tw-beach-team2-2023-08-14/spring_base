package com.example.domain.factory;

import static com.example.domain.entity.OrderStatus.CREATED;

import com.example.domain.entity.Order;
import com.example.domain.entity.ProductDetail;
import java.math.BigDecimal;
import java.util.List;

public class OrderFactory {
  private OrderFactory() {
    String exceptionMessage = "Class: " + OrderFactory.class + " should not be instantiated.";
    throw new UnsupportedOperationException(exceptionMessage);
  }

  public static Order createOrder(List<ProductDetail> productDetailList, String customerId) {

    BigDecimal totalPrice = calculateTotalPrice(productDetailList);

    return Order.builder()
        .customerId(customerId)
        .totalPrice(totalPrice)
        .status(CREATED)
        .productDetails(productDetailList)
        .build();
  }

  private static BigDecimal calculateTotalPrice(List<ProductDetail> productDetailList) {
    return productDetailList.stream()
        .map(
            productDetail ->
                productDetail.getPrice().multiply(BigDecimal.valueOf(productDetail.getAmount())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
