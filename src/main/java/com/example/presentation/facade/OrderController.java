package com.example.presentation.facade;

import com.example.application.service.OrderApplicationService;
import com.example.domain.entity.OrderCreation;
import com.example.presentation.vo.OrderDto;
import com.example.presentation.vo.OrderReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderApplicationService orderApplicationService;

  @PostMapping
  public OrderCreation createOrder(@RequestBody OrderReqDto orderReqDto)
      throws JsonProcessingException {
    return orderApplicationService.createOrder(orderReqDto);
  }

  @GetMapping
  public List<OrderDto> retrieveOrderList(@RequestParam("customerId") UUID customer_id) {
    return orderApplicationService.findOrderByCustomerId(customer_id.toString());
  }
}
