package com.example.application.assembler;

import static org.mapstruct.factory.Mappers.getMapper;

import com.example.domain.entity.Customer;
import com.example.presentation.vo.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {
  CustomerDtoMapper MAPPER = getMapper(CustomerDtoMapper.class);

  CustomerDto toDto(Customer customer);
}
