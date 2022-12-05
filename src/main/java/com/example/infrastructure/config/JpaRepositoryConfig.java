package com.example.infrastructure.config;


import com.example.common.base.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class,
    basePackages = "com.example.infrastructure.persistence.repository")
@Configuration
public class JpaRepositoryConfig {
}
