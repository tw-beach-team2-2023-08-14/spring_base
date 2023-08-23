package com.example.domain.entity;

import static com.example.common.exception.BaseExceptionCode.INVALID_CONSUMER_ID;
import static com.example.common.exception.BaseExceptionCode.INVALID_ORDER_STATUS;
import static com.example.domain.entity.OrderStatus.CANCELLED;

import com.example.common.exception.BusinessException;
import com.example.domain.util.ProductDetailSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {
  private Integer id;

  private String customerId;

  @Builder.Default private String orderId = UUID.randomUUID().toString();

  private BigDecimal primitiveTotalPrice;

  private BigDecimal totalPrice;

  private OrderStatus status;

  @Builder.Default private LocalDateTime createTime = LocalDateTime.now();

  @Builder.Default private LocalDateTime updateTime = LocalDateTime.now();

  @JsonSerialize(using = ProductDetailSerializer.class)
  private List<ProductDetail> productDetails;

  public void calculatePrimitiveTotalPrice() {
    this.primitiveTotalPrice =
        productDetails.stream()
            .map(
                productDetail ->
                    productDetail
                        .getPrice()
                        .multiply(BigDecimal.valueOf(productDetail.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);
  }

  public void cancel(String customerId) {

    checkOrderBelonging(customerId);
    if (status == CANCELLED) {
      throw new BusinessException(INVALID_ORDER_STATUS, "Order already in cancelled stage.");
    }
    setStatus(CANCELLED);
  }

  private void checkOrderBelonging(String customerId) {
    if (!customerId.equals(this.customerId))
      throw new BusinessException(INVALID_CONSUMER_ID, "Customer id not match.");
  }
}
