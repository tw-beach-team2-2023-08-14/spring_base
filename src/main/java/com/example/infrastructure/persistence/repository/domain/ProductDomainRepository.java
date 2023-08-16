package com.example.infrastructure.persistence.repository.domain;

import static com.example.common.exception.BaseExceptionCode.NOT_FOUND_PRODUCT;
import static com.example.common.exception.NotFoundException.notFoundException;

import com.example.domain.entity.Product;
import com.example.domain.repository.ProductRepository;
import com.example.infrastructure.persistence.assembler.ProductDataMapper;
import com.example.infrastructure.persistence.entity.ProductPo;
import com.example.infrastructure.persistence.repository.JpaProductRepository;
import java.util.List;
import java.util.Optional;
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
  public Product findById(Integer productId) {
    Optional<ProductPo> productPo = jpaProductRepository.findById(productId);
    productPo.orElseThrow(notFoundException(NOT_FOUND_PRODUCT));
    return mapper.toDo(productPo.get());
  }

  @Override
  public List<Product> findAllByIds(List<Integer> productIds) {
    return jpaProductRepository.findAllById(productIds).stream()
        .map(mapper::toDo)
        .collect(Collectors.toList());
  }
}
