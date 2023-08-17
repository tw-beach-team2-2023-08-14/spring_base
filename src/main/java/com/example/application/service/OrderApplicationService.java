package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;

import com.example.domain.entity.Order;
import com.example.domain.entity.OrderCreation;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderApplicationService {

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public List<OrderListDto> findOrderByCustomerIdAndOrderId(String customerId, String orderId) {
    return orderRepository.findByCustomerId(customerId).stream()
        .filter(order -> orderId == null || orderId.equals(order.getOrderId()))
        .peek(Order::calculatePrimitiveTotalPrice)
        .map(MAPPER::toDto)
        .toList();
  }

  @Transactional
  public OrderCreation createOrder(OrderReqDto orderReqDto) throws JsonProcessingException {

    List<Product> products =
        productRepository.findAllByIds(
            orderReqDto.getOrderProducts().stream().map(OrderProductReqDto::getProductId).toList());

    Map<Integer, Integer> productIdQuantityMap =
        extractProductsOrderedQuantity(orderReqDto.getOrderProducts());

    List<ProductDetail> productDetails = extractProductDetails(products, productIdQuantityMap);

    Order order = OrderFactory.createOrder(productDetails, orderReqDto.getCustomerId());
    updateInventory(products, productIdQuantityMap);
    return new OrderCreation(orderRepository.save(order));
  }

  private void updateInventory(List<Product> products, Map<Integer, Integer> productIdQuantityMap) {
    products.forEach(
        (product) -> product.deductInventory(productIdQuantityMap.get(product.getId())));
    productRepository.updateProductsInventory(products);
  }

  private List<ProductDetail> extractProductDetails(
      List<Product> productList, Map<Integer, Integer> productIdQuantityMap) {
    return productList.stream()
        .map(
            (product) ->
                OrderFactory.extractProductDetailFromProduct(
                    product, productIdQuantityMap.get(product.getId())))
        .toList();
  }

  private static Map<Integer, Integer> extractProductsOrderedQuantity(
      List<OrderProductReqDto> orderProductDtoList) {
    return orderProductDtoList.stream()
        .collect(
            Collectors.toMap(OrderProductReqDto::getProductId, OrderProductReqDto::getQuantity));
  }
}
