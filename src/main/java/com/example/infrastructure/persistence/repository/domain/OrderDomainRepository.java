package com.example.infrastructure.persistence.repository.domain;

import com.example.domain.entity.Order;
import com.example.domain.repository.OrderRepository;
import com.example.infrastructure.persistence.assembler.OrderDataMapper;
import com.example.infrastructure.persistence.repository.JpaOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderDomainRepository implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;
  private final OrderDataMapper mapper = OrderDataMapper.mapper;

  public List<Order> findByCustomerId(String customerId) {
    return jpaOrderRepository.findByCustomerId(customerId).stream().map(mapper::toDo).toList();
  }

  @Override
  public Integer save(Order order) throws JsonProcessingException {
    return jpaOrderRepository.save(mapper.toPo(order)).getId();
  }
}
