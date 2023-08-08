package com.example.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

  private BigDecimal totalPrice;

  private String status;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private String productDetails;
}
