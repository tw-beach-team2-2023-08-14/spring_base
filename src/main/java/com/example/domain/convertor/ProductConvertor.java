package com.example.domain.convertor;

import com.example.domain.entity.Product;
import com.example.domain.entity.ProductDetail;

public class ProductConvertor {
  private ProductConvertor() {}

  public static ProductConvertor generateProductConvertor() {
    return new ProductConvertor();
  }

  public ProductDetail toProductDetail(Product product) {
    return new ProductDetail(product.getId(), product.getName(), product.getPrice(), null);
  }
}
