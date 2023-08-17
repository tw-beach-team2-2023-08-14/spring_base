package com.example.infrastructure.persistence.repository.domain;

import static com.example.common.exception.BaseExceptionCode.NOT_FOUND_PRODUCT;

import com.example.common.exception.ExceptionCode;
import com.example.common.exception.NotFoundException;
import com.example.domain.entity.Product;
import com.example.domain.repository.ProductRepository;
import com.example.infrastructure.persistence.assembler.ProductDataMapper;
import com.example.infrastructure.persistence.entity.ProductPo;
import com.example.infrastructure.persistence.repository.JpaProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductDomainRepository implements ProductRepository {
  private final JpaProductRepository jpaProductRepository;
  private final ProductDataMapper mapper = ProductDataMapper.mapper;

  @Override
  public List<Product> findAll() {
    return jpaProductRepository.findAll().stream().map(mapper::toDo).toList();
  }

  @Override
  public List<Product> findAllByIds(List<Integer> productIds) {
    List<Product> products =
        jpaProductRepository.findAllById(productIds).stream()
            .map(mapper::toDo)
            .collect(Collectors.toList());

    if (products.size() != productIds.size()) {
      throw new NotFoundException(ExceptionCode.NOT_FOUND, NOT_FOUND_PRODUCT, "Not found product.");
    }

    return products;
  }

  @Override
  public List<Product> updateProductsInventory(List<Product> products) {
    List<ProductPo> productPoList = products.stream().map(mapper::toPo).toList();
    return jpaProductRepository.saveAll(productPoList).stream().map(mapper::toDo).toList();
  }
}
