package com.example.presentation.vo;

import com.example.domain.entity.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderListDto {
  private Integer id;

  private String customerId;

  private BigDecimal totalPrice;

  private OrderStatus status;

  private List<OrderProductDetailDto> productDetails;
}
