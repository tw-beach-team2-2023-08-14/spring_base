package com.example.common.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T, D> extends JpaRepository<T, D> {
  JPAQueryFactory getJpaQueryFactory();
}
