package com.example.domain.entity;

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

  private Integer inventory;

  public Product(
      Integer id,
      String name,
      BigDecimal price,
      BigDecimal discount,
      ProductStatus status,
      Integer inventory) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.status = status;
    this.salePrice = calculateDiscount();
    this.inventory = inventory;
  }

  public BigDecimal calculateDiscount() {
    if (price == null) {
      return null;
    }
    if (discount == null) {
      discount = BigDecimal.ONE;
    }
    BigDecimal multiply = price.multiply(discount);
    return multiply.setScale(4, RoundingMode.HALF_UP);
  }

  public Boolean isValid() {
    return status == ProductStatus.VALID;
  }

  public ProductDetail toProductDetail(Long amount) {
    return new ProductDetail(id, name, price, discount, amount);
  }

  public Boolean hasSufficientInventory(Long quantity) {
    if (inventory == null || inventory <= 0) {
      return false;
    }
    return inventory >= quantity.intValue();
  }
}
