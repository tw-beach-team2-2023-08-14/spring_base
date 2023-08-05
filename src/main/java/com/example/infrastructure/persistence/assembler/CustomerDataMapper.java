package com.example.infrastructure.persistence.assembler;

import static org.mapstruct.factory.Mappers.getMapper;

import com.example.domain.entity.Customer;
import com.example.infrastructure.persistence.entity.CustomerPo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDataMapper {
  CustomerDataMapper mapper = getMapper(CustomerDataMapper.class);

  Customer toDo(CustomerPo customerPo);
}
