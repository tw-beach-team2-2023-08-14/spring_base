package com.example.domain.entity;

import java.math.BigDecimal;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal salePrice;

  private Long amount;
}
