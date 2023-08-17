package com.example.domain.entity;

import static com.example.common.exception.BaseExceptionCode.INSUFFICIENT_PRODUCT;

import com.example.common.exception.BusinessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;

@NoArgsConstructor
@Data
public class Product {
  public static final BigDecimal LOWEST_DISCOUNT_PRICE = BigDecimal.valueOf(0.01);
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
    this.salePrice = calculateDiscountPricePrice();
    this.inventory = inventory;
  }

  public BigDecimal calculateDiscountPricePrice() {
    if (price == null) {
      return null;
    }
    if (discount == null) {
      discount = BigDecimal.ONE;
    }
    BigDecimal discountPrice = price.multiply(discount);
    if (discountPrice.compareTo(LOWEST_DISCOUNT_PRICE) <= 0) {
      return LOWEST_DISCOUNT_PRICE;
    }
    return discountPrice.setScale(2, RoundingMode.HALF_UP);
  }

  public Boolean isValid() {
    return status == ProductStatus.VALID;
  }

  public Boolean isValidPrice() {
    return price != null;
  }

  public ProductDetail toProductDetail(Integer quantity) {
    return new ProductDetail(id, name, price, calculateDiscountPricePrice(), quantity);
  }

  public Boolean hasSufficientInventory(Integer quantity) {
    if (inventory == null || inventory <= 0) {
      return false;
    }
    return inventory >= quantity;
  }

  public void deductInventory(Integer deductAmount) {
    if (deductAmount > inventory) {
      throw new BusinessException(
          INSUFFICIENT_PRODUCT, "Product of id [" + id + "] is insufficient");
    }
    setInventory(inventory - deductAmount);
  }
}
