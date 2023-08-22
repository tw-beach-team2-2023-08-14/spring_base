package com.example.infrastructure.persistence.repository.domain;

import static com.example.common.exception.BaseExceptionCode.NOT_FOUND_ORDER;
import static com.example.infrastructure.persistence.assembler.OrderDataMapper.MAPPER;

import com.example.common.exception.BusinessException;
import com.example.common.exception.ExceptionCode;
import com.example.common.exception.NotFoundException;
import com.example.domain.entity.Order;
import com.example.domain.repository.OrderRepository;
import com.example.infrastructure.persistence.assembler.OrderProductDetailsDataMapper;
import com.example.infrastructure.persistence.entity.OrderPo;
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
        .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND, "Not found Order."));
  }

  @Override
  public Order findByCustomerIdAndOrderId(String customerId, String orderId) {
    OrderPo orderPo = jpaOrderRepository.findByCustomerIdAndOrderId(customerId, orderId);
    if (orderPo == null) {
      throw new BusinessException(
          NOT_FOUND_ORDER,
          "Order of order id " + orderId + "and customer id " + customerId + "not found");
    }
    return orderProductDetailsDataMapper.mapOrderPoToOrder(orderPo);
  }

  @Override
  public String save(Order order) throws JsonProcessingException {
    return jpaOrderRepository.save(MAPPER.toPo(order)).getOrderId();
  }
}
