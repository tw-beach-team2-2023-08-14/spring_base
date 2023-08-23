package com.example.infrastructure.persistence.repository;

import com.example.common.base.JpaAndQueryDslExecutor;
import com.example.infrastructure.persistence.entity.OrderPo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface JpaOrderRepository extends JpaAndQueryDslExecutor<OrderPo, String> {
  List<OrderPo> findByCustomerId(String customerId);

  OrderPo findByCustomerIdAndOrderId(String customerId, String orderId);

  @Query(
      nativeQuery = true,
      value = "select * from customer_order where order_id = :orderId for update")
  Optional<OrderPo> lockAndFindByOrderId(String orderId);
}
