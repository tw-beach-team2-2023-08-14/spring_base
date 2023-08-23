package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;

import com.example.domain.entity.Order;
import com.example.domain.entity.OrderCreation;
import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;
import com.example.domain.factory.OrderFactory;
import com.example.domain.repository.OrderRepository;
import com.example.domain.repository.ProductRepository;
import com.example.presentation.vo.OrderDto;
import com.example.presentation.vo.OrderProductReqDto;
import com.example.presentation.vo.OrderReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderApplicationService {

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public List<OrderDto> findOrderByCustomerId(String customerId) {
    return orderRepository.findByCustomerId(customerId).stream()
        .peek(Order::calculatePrimitiveTotalPrice)
        .map(MAPPER::toDto)
        .toList();
  }

  public List<OrderDto> findOrderByCustomerIdAndOrderId(String customerId, String orderId) {
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

  @Transactional
  public void cancelOrder(String customerId, String orderId) throws JsonProcessingException {
    Order order = orderRepository.lockAndFindByOrderId(orderId);
    order.cancel(customerId);
    orderRepository.save(order);
    incrementProductInventory(order);
  }

  private void updateInventory(List<Product> products, Map<Integer, Integer> productIdQuantityMap) {
    products.forEach(
        (product) -> product.updateInventory(productIdQuantityMap.get(product.getId())));
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
            Collectors.toMap(
                OrderProductReqDto::getProductId,
                (orderProductDto) -> Math.negateExact(orderProductDto.getQuantity())));
  }

  private void incrementProductInventory(Order order) {
    List<ProductDetail> productDetails = order.getProductDetails();
    List<Integer> orderProductIds = getPropertyList(productDetails, ProductDetail::getId);
    List<Product> products = productRepository.lockAndFindAllByIds(orderProductIds);

    Map<Integer, Integer> productIdQuantityMap =
        listToMap(productDetails, ProductDetail::getId, ProductDetail::getQuantity);

    updateInventory(products, productIdQuantityMap);
  }

  private static <K, T, J> Map<K, J> listToMap(
      List<T> list, Function<T, K> keyExtractor, Function<T, J> valueExtractor) {
    return list.stream().collect(Collectors.toMap(keyExtractor, valueExtractor));
  }

  private static <K, T> List<T> getPropertyList(List<K> list, Function<K, T> propertyExtractor) {
    return list.stream().map(propertyExtractor).toList();
  }
}
