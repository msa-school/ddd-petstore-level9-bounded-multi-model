package com.example.petstore.domain;


import com.example.petstore.domain.*;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long>{    // Repository Pattern Interface  // Dependency Inversion Principle

    Optional<Item> findByPetId(Long petId); // select * from item where petId=<petId>
  
}