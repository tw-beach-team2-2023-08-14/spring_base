package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;
import static com.example.domain.entity.OrderStatus.CREATED;

import com.example.domain.entity.Order;
import com.example.domain.entity.ProductDetail;
import com.example.domain.repository.OrderRepository;
import com.example.domain.service.OrderService;
import com.example.domain.util.OrderUtil;
import com.example.presentation.vo.OrderListDto;
import com.example.presentation.vo.OrderReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderApplicationService {
  private final OrderService orderService;

  private final OrderRepository orderRepository;

  public List<OrderListDto> findOrderByCustomerIdAndOrderId(String customerId, String orderId) {
    return orderRepository.findByCustomerId(customerId).stream()
        .map(MAPPER::toDto)
        .filter(orderListDto -> orderId == null || orderId.equals(orderListDto.getOrderId()))
        .toList();
  }

  public String createOrder(OrderReqDto orderReqDto) throws JsonProcessingException {

    List<ProductDetail> productDetails =
        orderService.extractProductDetails(orderReqDto.getOrderProducts());

    BigDecimal totalPrice = OrderUtil.calculateTotalPrice(productDetails);

    Order order =
        Order.builder()
            .customerId(orderReqDto.getCustomerId())
            .totalPrice(totalPrice)
            .status(CREATED)
            .productDetails(productDetails)
            .build();

    return orderRepository.save(order);
  }
}
