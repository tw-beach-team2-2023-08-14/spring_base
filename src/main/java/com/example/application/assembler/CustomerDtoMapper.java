package com.example.application.assembler;

import com.example.domain.entity.Customer;
import com.example.presentation.vo.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {
  CustomerDtoMapper MAPPER = getMapper(CustomerDtoMapper.class);

  CustomerDto toDto(Customer customer);

}
