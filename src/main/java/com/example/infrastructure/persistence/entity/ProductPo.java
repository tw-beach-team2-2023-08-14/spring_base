package com.example.infrastructure.persistence.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "product")
public class ProductPo {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;
  private String name;
  private BigDecimal price;
  private String status;
  private BigDecimal discount;
  private BigDecimal salePrice;
  private Integer inventory;
}
