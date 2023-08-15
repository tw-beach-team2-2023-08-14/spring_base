package com.example.presentation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderProductReqDto {
  private Integer productId;
  private Long quantity;
}
