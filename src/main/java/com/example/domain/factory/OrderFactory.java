package com.example.domain.factory;

import static com.example.common.exception.BaseExceptionCode.INVALID_PRODUCT;
import static com.example.domain.entity.OrderStatus.CREATED;

import com.example.common.exception.BusinessException;
import com.example.domain.entity.Order;
import com.example.domain.entity.Product;
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

  public static ProductDetail extractProductDetailFromProduct(Product product, Long quantity) {
    checkValidStatus(product);
    return product.toProductDetail(quantity);
  }

  private static void checkValidStatus(Product product) {
    if (!product.isValid()) {
      throw new BusinessException(
          INVALID_PRODUCT, "Product of id [" + product.getId() + "] is invalid");
    }
  }
}
