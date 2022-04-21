package com.example.petstore;


import com.example.petstore.domain.Pet;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long>{    // Repository Pattern Interface
  
}
