package com.example.domain.convertor;

import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;

public class ProductConvertor {
  private ProductConvertor() {
    String exceptionMessage = "Class: " + ProductConvertor.class + " should not be instantiated.";
    throw new UnsupportedOperationException(exceptionMessage);
  }

  public static ProductDetail toProductDetail(Product product) {
    return new ProductDetail(product.getId(), product.getName(), product.getPrice(), null);
  }
}
