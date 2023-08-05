package com.example.infrastructure.persistence.repository;

import com.example.common.base.JpaAndQueryDslExecutor;
import com.example.infrastructure.persistence.entity.CustomerPo;

public interface JpaCustomerRepository extends JpaAndQueryDslExecutor<CustomerPo, String> {}
