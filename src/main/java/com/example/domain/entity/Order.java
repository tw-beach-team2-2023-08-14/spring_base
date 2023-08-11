package com.example.domain.entity;

import com.example.domain.util.OrderUtil;
import com.example.domain.util.ProductDetailSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
  private Integer id;

  private String customerId;

  @Builder.Default private String orderId = OrderUtil.generateOrderId();

  private BigDecimal totalPrice;

  private OrderStatus status;

  @Builder.Default private LocalDateTime createTime = LocalDateTime.now();

  @Builder.Default private LocalDateTime updateTime = LocalDateTime.now();

  @JsonSerialize(using = ProductDetailSerializer.class)
  private List<ProductDetail> productDetails;
}
