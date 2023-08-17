package com.example.domain.repository;

import com.example.domain.entity.Product;
import java.util.List;

public interface ProductRepository {
  List<Product> findAll();

  List<Product> findAllByIds(List<Integer> productIds);

  List<Product> updateProductsInventory(List<Product> products);
}
