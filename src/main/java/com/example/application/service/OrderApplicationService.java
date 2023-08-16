package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;

import com.example.domain.entity.Order;
import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;
import com.example.domain.factory.OrderFactory;
import com.example.domain.repository.OrderRepository;
import com.example.domain.repository.ProductRepository;
import com.example.presentation.vo.OrderListDto;
import com.example.presentation.vo.OrderProductReqDto;
import com.example.presentation.vo.OrderReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderApplicationService {

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public List<OrderListDto> findOrderByCustomerIdAndOrderId(String customerId, String orderId) {
    return orderRepository.findByCustomerId(customerId).stream()
        .map(MAPPER::toDto)
        .filter(orderListDto -> orderId == null || orderId.equals(orderListDto.getOrderId()))
        .toList();
  }

  public String createOrder(OrderReqDto orderReqDto) throws JsonProcessingException {
    List<Product> products =
        productRepository.findAllByIds(
            orderReqDto.getOrderProducts().stream().map(OrderProductReqDto::getProductId).toList());

    List<ProductDetail> productDetails =
        extractProductDetails(products, orderReqDto.getOrderProducts());

    Order order = OrderFactory.createOrder(productDetails, orderReqDto.getCustomerId());

    return orderRepository.save(order);
  }

  private List<ProductDetail> extractProductDetails(
      List<Product> productList, List<OrderProductReqDto> orderProductDtoList) {

    Map<Integer, Long> productIdQuantityMap =
        orderProductDtoList.stream()
            .collect(
                Collectors.toMap(
                    OrderProductReqDto::getProductId, OrderProductReqDto::getQuantity));

    return productList.stream()
        .map(
            (product) ->
                OrderFactory.extractProductDetailFromProduct(
                    product, productIdQuantityMap.get(product.getId())))
        .toList();
  }
}
