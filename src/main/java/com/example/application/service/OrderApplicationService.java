package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;

import com.example.domain.repository.OrderRepository;
import com.example.presentation.vo.OrderListDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderApplicationService {
  private final OrderRepository orderRepository;

  public List<OrderListDto> findByCustomerId(String customerId) {
    return orderRepository.findByCustomerId(customerId).stream().map(MAPPER::toDto).toList();
  }
}
