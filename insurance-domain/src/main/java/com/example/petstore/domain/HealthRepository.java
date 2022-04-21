package com.example.petstore.domain;

import org.springframework.data.repository.CrudRepository;

public interface HealthRepository extends CrudRepository<Health, Long>{    // Repository Pattern Interface
  
}