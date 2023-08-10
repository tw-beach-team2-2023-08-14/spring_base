package com.example.infrastructure.persistence.assembler;

import static org.mapstruct.factory.Mappers.getMapper;

import com.example.domain.entity.Order;
import com.example.domain.entity.ProductDetail;
import com.example.infrastructure.persistence.entity.OrderPo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDataMapper {
  OrderDataMapper MAPPER = getMapper(OrderDataMapper.class);

  @Mapping(source = "productDetails", target = "productDetails")
  Order toDo(OrderPo orderPo, List<ProductDetail> productDetails);

  @Mapping(
      target = "productDetails",
      expression = "java(mapToProductDetails(order.getProductDetails()))")
  OrderPo toPo(Order order) throws JsonProcessingException;

  default String mapToProductDetails(List<ProductDetail> productDetails)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(productDetails);
  }
}
