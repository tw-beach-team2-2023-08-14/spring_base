package com.example.presentation.vo;

import java.math.BigDecimal;
import lombok.*;

@Data
public class ProductDto {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal discount;

  private BigDecimal salePrice;

  private String status;
}
