package com.example.presentation.vo;

import com.example.domain.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderListDto {
  private Integer id;

  private String customerId;

  private String orderId;

  private BigDecimal primitiveTotalPrice;

  private BigDecimal totalPrice;

  private OrderStatus status;

  private LocalDateTime createTime;

  private List<OrderProductDetailDto> productDetails;
}
