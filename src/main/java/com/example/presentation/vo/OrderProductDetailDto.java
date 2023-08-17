package com.example.presentation.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderProductDetailDto {
  private Integer id;

  private String name;

  private BigDecimal price;

  private BigDecimal salePrice;

  private Long amount;
}
