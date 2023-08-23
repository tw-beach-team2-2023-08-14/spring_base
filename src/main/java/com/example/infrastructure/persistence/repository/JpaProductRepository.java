package com.example.infrastructure.persistence.repository;

import com.example.common.base.JpaAndQueryDslExecutor;
import com.example.infrastructure.persistence.entity.ProductPo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaAndQueryDslExecutor<ProductPo, Integer> {
  @Query(nativeQuery = true, value = "select * from product where id in :productIds for update")
  List<ProductPo> lockAndFindAllByIds(List<Integer> productIds);
}
