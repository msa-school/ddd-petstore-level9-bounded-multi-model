package com.example.template;

import com.example.template.domain.Pet;
import com.example.template.domain.Repository;

public class MySQLRepositoryImpl extends Repository{

    @Override
    public void save(Pet pet){
        System.out.println("MYSQL: insert into pet(health, appearance) values (" + pet.getHealth() + ","+ pet.getAppearance() + ")");
    }
    
}
