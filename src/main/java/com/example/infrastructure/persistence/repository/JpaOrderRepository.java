package com.example.infrastructure.persistence.repository;

import com.example.common.base.JpaAndQueryDslExecutor;
import com.example.infrastructure.persistence.entity.OrderPo;
import java.util.List;

public interface JpaOrderRepository extends JpaAndQueryDslExecutor<OrderPo, String> {
  List<OrderPo> findByCustomerId(String customerId);

  OrderPo findByCustomerIdAndOrderId(String customerId, String orderId);
}
