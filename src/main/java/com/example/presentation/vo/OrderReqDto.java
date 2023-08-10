package com.example.presentation.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderReqDto {

  private String customerId;

  private List<OrderProductReqDto> orderProducts;
}
