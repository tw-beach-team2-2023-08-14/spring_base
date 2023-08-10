package com.example.infrastructure.persistence.assembler;

import static com.example.infrastructure.persistence.assembler.OrderDataMapper.MAPPER;

import com.example.domain.entity.Order;
import com.example.domain.entity.ProductDetail;
import com.example.infrastructure.persistence.entity.OrderPo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderProductDetailsDataMapper {
  public Order mapOrderPoToOrder(OrderPo orderPo) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<ProductDetail> productDetails =
          objectMapper.readValue(orderPo.getProductDetails(), new TypeReference<>() {});
      return MAPPER.toDo(orderPo, productDetails);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
