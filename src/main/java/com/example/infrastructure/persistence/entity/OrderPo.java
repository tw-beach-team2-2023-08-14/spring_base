package com.example.infrastructure.persistence.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.domain.entity.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class OrderPo {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;

  private String customerId;

  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private LocalDateTime createTime = LocalDateTime.now();

  private LocalDateTime updateTime = LocalDateTime.now();

  @Column(columnDefinition = "json")
  private String productDetails;
}
