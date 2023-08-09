package com.example.infrastructure.persistence.assembler;

import static org.mapstruct.factory.Mappers.getMapper;

import com.example.domain.entity.Order;
import com.example.domain.entity.OrderItem;
import com.example.infrastructure.persistence.entity.OrderPo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDataMapper {
  OrderDataMapper mapper = getMapper(OrderDataMapper.class);

  Order toDo(OrderPo orderPo);

  @Mapping(
      target = "productDetails",
      expression = "java(mapToProductDetails(order.getOrderItems()))")
  OrderPo toPo(Order order);

  default String mapToProductDetails(List<OrderItem> orderItems) {
    return orderItems.toString();
  }
}
