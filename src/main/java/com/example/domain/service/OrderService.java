package com.example.domain.service;

import static com.example.common.exception.BaseExceptionCode.INVALID_PRODUCT;

import com.example.common.exception.BusinessException;
import com.example.domain.convertor.ProductConvertor;
import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;
import com.example.domain.repository.ProductRepository;
import com.example.presentation.vo.OrderProductReqDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
  private final ProductRepository productRepository;

  public List<ProductDetail> extractProductDetails(List<OrderProductReqDto> orderProducts) {
    return orderProducts.stream().map(this::extractProductDetailFromOrderRequest).toList();
  }

  private ProductDetail extractProductDetailFromOrderRequest(
      OrderProductReqDto orderProductReqDto) {
    Product product = productRepository.findById(orderProductReqDto.getProductId());
    checkValidStatus(product);
    ProductDetail productDetail = ProductConvertor.toProductDetail(product);
    productDetail.setAmount(orderProductReqDto.getQuantity());
    return productDetail;
  }

  private static void checkValidStatus(Product product) {
    if (!product.isValid()) {
      throw new BusinessException(
          INVALID_PRODUCT, "Product of id [" + product.getId() + "] is invalid");
    }
  }
}
