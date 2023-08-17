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

  public ProductDetail(
      Integer id, String name, BigDecimal price, BigDecimal salePrice, Long amount) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.salePrice = salePrice;
    this.amount = amount;
    this.totalPreferentialPrice = calculateTotalPreferentialPrice();
  }

  private BigDecimal calculateTotalPreferentialPrice() {
    return price
        .multiply(BigDecimal.valueOf(amount))
        .subtract(salePrice.multiply(BigDecimal.valueOf(amount)));
  }
}
