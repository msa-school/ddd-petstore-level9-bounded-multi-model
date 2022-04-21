package com.example.petstore;


import com.example.petstore.domain.*;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String>{    // Repository Pattern Interface
  
}