package com.example.presentation.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductDetailDto {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal salePrice;

  private Integer quantity;

  private BigDecimal totalPreferentialPrice;
}
