package com.example.infrastructure.persistence.repository.domain;

import static com.example.common.exception.BaseExceptionCode.NON_EXIST_ORDER;
import static com.example.common.exception.BaseExceptionCode.NOT_FOUND_ORDER;
import static com.example.infrastructure.persistence.assembler.OrderDataMapper.MAPPER;

import com.example.common.exception.BusinessException;
import com.example.domain.entity.Order;
import com.example.domain.repository.OrderRepository;
import com.example.infrastructure.persistence.assembler.OrderProductDetailsDataMapper;
import com.example.infrastructure.persistence.repository.JpaOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderDomainRepository implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;
  private final OrderProductDetailsDataMapper orderProductDetailsDataMapper;

  public List<Order> findByCustomerId(String customerId) {
    return jpaOrderRepository.findByCustomerId(customerId).stream()
        .map(orderProductDetailsDataMapper::mapOrderPoToOrder)
        .toList();
  }

  public Order lockAndFindByOrderId(String orderId) {
    return jpaOrderRepository
        .lockAndFindByOrderId(orderId)
        .map(orderProductDetailsDataMapper::mapOrderPoToOrder)
        .orElseThrow(() -> new BusinessException(NON_EXIST_ORDER, "Order does not exist."));
  }

  @Override
  public Order findByCustomerIdAndOrderId(String customerId, String orderId) {
    return jpaOrderRepository
        .findByCustomerIdAndOrderId(customerId, orderId)
        .map(orderProductDetailsDataMapper::mapOrderPoToOrder)
        .orElseThrow(
            () ->
                new BusinessException(
                    NOT_FOUND_ORDER,
                    "Order of order id "
                        + orderId
                        + "and customer id "
                        + customerId
                        + "not found"));
  }

  @Override
  public String save(Order order) throws JsonProcessingException {
    return jpaOrderRepository.save(MAPPER.toPo(order)).getOrderId();
  }
}
