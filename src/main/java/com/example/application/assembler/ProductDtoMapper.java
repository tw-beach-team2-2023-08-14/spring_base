package com.example.application.assembler;

import static org.mapstruct.factory.Mappers.getMapper;

import com.example.domain.entity.Product;
import com.example.presentation.vo.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductDtoMapper {
  ProductDtoMapper MAPPER = getMapper(ProductDtoMapper.class);

  @Mapping(target = "salePrice", expression = "java(product.calculateDiscountPricePrice())")
  ProductDto toDto(Product product);
}
