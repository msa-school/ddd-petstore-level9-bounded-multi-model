package com.example.template;

import java.util.ArrayList;
import java.util.List;

import com.example.template.domain.Cat;
import com.example.template.domain.Dog;
import com.example.template.domain.Human;
import com.example.template.domain.Pet;
import com.example.template.domain.Repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static ApplicationContext applicationContext;
    public static void main(String[] args) throws Exception{

        // Dependency Inversion


        // Dynamic casting
        boolean drunk = true;
        

        Pet somePet = new Human();   
        if(drunk)
            somePet = new Dog();

        System.out.println(somePet.getClass());
        

        // Standardized Batch processing //polymorphism
        List<Pet> pets = new ArrayList<Pet>();
        pets.add(new Cat());
        pets.add(new Dog());

        boolean mysqlIsPreferred = true;

        Repository repository = (mysqlIsPreferred ? new MySQLRepositoryImpl() : new OracleRepositoryImpl());   //dynamic casting // dependency inversion

        int newHealth = 100;

        pets.forEach(pet ->{
            System.out.println("건강 " + pet.getHealth());
            pet.repository = repository;
            pet.feed();

            
//            if(newHealth > 90) throw new RuntimeException("health 는 90 넘으면 안됨");  // Anemic domain model
            pet.setHealth(100);

            System.out.println("건강 " + pet.getHealth());

            pet.save();
        });


       applicationContext = SpringApplication.run(Application.class, args);
    }


}


