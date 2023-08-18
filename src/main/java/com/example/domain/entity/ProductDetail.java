package com.example.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

  private Integer quantity;

  public ProductDetail(
      Integer id, String name, BigDecimal price, BigDecimal salePrice, Integer quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.salePrice = salePrice;
    this.quantity = quantity;
    this.totalPreferentialPrice = calculateTotalPreferentialPrice();
  }

  private BigDecimal calculateTotalPreferentialPrice() {
    return price
        .multiply(BigDecimal.valueOf(quantity))
        .subtract(salePrice.multiply(BigDecimal.valueOf(quantity)))
        .setScale(2, RoundingMode.HALF_UP);
  }
}
