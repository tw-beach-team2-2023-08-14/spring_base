package com.example.application.service;

import com.example.domain.entity.Product;

@FunctionalInterface
public interface InventoryAdjuster {
  void adjust(Product product, int quantity);
}
