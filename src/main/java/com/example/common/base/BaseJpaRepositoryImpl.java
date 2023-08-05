package com.example.common.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseJpaRepositoryImpl<T, D> extends SimpleJpaRepository<T, D>
    implements BaseJpaRepository<T, D> {

  private final EntityManager entityManager;

  public BaseJpaRepositoryImpl(
      JpaEntityInformation<T, D> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public JPAQueryFactory getJpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
