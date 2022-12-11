package com.example.domain.repository;

import com.example.domain.entity.Customer;

public interface CustomerRepository {
  Customer findById(String id);
}
