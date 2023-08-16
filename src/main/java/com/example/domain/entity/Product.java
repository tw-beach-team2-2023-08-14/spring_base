package com.example.domain.entity;

import com.example.presentation.vo.ProductStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Product {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal discount;

  private BigDecimal salePrice;

  private ProductStatus status;

  public Product(Integer id, String name, BigDecimal price, BigDecimal discount, ProductStatus status) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.status = status;
    this.salePrice = price == null ? null : calculateDiscount();
  }

  private BigDecimal calculateDiscount() {
    if (discount == null) {
      discount = BigDecimal.ONE;
    }
    BigDecimal multiply = price.multiply(discount);
    return multiply.setScale(4, RoundingMode.HALF_UP);
  }
}
