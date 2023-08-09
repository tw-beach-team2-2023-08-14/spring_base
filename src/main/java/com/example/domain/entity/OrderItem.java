package com.example.domain.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItem {
  private String id;
  private String name;
  private BigDecimal price;
  private long amount;
}
