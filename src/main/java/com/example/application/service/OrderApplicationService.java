package com.example.application.service;

import static com.example.application.assembler.OrderListDtoMapper.MAPPER;
import static com.example.common.exception.BaseExceptionCode.INVALID_PRODUCT;

import com.example.common.exception.BusinessException;
import com.example.domain.convertor.ProductConvertor;
import com.example.domain.entity.Order;
import com.example.domain.entity.OrderStatus;
import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;
import com.example.domain.repository.OrderRepository;
import com.example.domain.repository.ProductRepository;
import com.example.domain.util.OrderIdGenerator;
import com.example.presentation.vo.OrderListDto;
import com.example.presentation.vo.OrderProductReqDto;
import com.example.presentation.vo.OrderReqDto;
import com.example.presentation.vo.ProductStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderApplicationService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public List<OrderListDto> findOrderByCustomerIdAndOrderId(String customerId, String orderId) {
    return orderRepository.findByCustomerId(customerId).stream()
        .map(MAPPER::toDto)
        .filter(orderListDto -> orderId == null || orderId.equals(orderListDto.getOrderId()))
        .toList();
  }

  public String createOrder(OrderReqDto orderReqDto) throws JsonProcessingException {

    ArrayList<ProductDetail> productDetails = getProductDetails(orderReqDto);

    BigDecimal totalPrice = calculateTotalPrice(productDetails);

    String orderId = OrderIdGenerator.generateOrderIdGenerator().generateOrderId();
    Order order =
        new Order(
            null,
            orderReqDto.getCustomerId(),
            orderId,
            totalPrice,
            OrderStatus.CREATED,
            LocalDateTime.now(),
            LocalDateTime.now(),
            productDetails);
    return orderRepository.save(order);
  }

  private static BigDecimal calculateTotalPrice(ArrayList<ProductDetail> productDetails) {
    return productDetails.stream()
        .map(
            productDetail ->
                productDetail.getPrice().multiply(BigDecimal.valueOf(productDetail.getAmount())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private ArrayList<ProductDetail> getProductDetails(OrderReqDto orderReqDto) {
    ArrayList<ProductDetail> productDetails = new ArrayList<>();

    ProductConvertor productConvertor = ProductConvertor.generateProductConvertor();

    for (OrderProductReqDto orderProduct : orderReqDto.getOrderProducts()) {
      Product product = productRepository.findById(orderProduct.getProductId());
      if (product.getStatus() == ProductStatus.INVALID) {
        throw new BusinessException(
            INVALID_PRODUCT, "Product of id [" + product.getId() + "] is invalid");
      }
      ProductDetail productDetail = productConvertor.toProductDetail(product);
      productDetail.setAmount(orderProduct.getQuantity());
      productDetails.add(productDetail);
    }
    return productDetails;
  }
}
