package com.example.domain.entity;

import java.math.BigDecimal;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDetail {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal salePrice;

  private BigDecimal totalPreferentialPrice;

  private Long amount;
}
