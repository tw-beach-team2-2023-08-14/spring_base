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
    BigDecimal discountPrice = price.multiply(discount).setScale(2, RoundingMode.HALF_UP);
    if (discountPrice.compareTo(BigDecimal.ZERO) <= 0) {
      discountPrice = BigDecimal.valueOf(0.01);
    }
    return discountPrice;
  }

  public Boolean isValid() {
    return status == ProductStatus.VALID;
  }

  public Boolean isValidPrice() {
    return price != null;
  }

  public ProductDetail toProductDetail(Long amount) {
    return new ProductDetail(
        id, name, price, calculateDiscount(), calculateTotalPreferentialPrice(amount), amount);
  }

  private BigDecimal calculateTotalPreferentialPrice(Long amount) {
    return price
        .multiply(BigDecimal.valueOf(amount))
        .subtract(calculateDiscount().multiply(BigDecimal.valueOf(amount)));
  }

  public Boolean hasSufficientInventory(Long quantity) {
    if (inventory == null || inventory <= 0) {
      return false;
    }
    return inventory >= quantity.intValue();
  }
}
