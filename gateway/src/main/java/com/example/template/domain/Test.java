package com.example.template.domain;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String args[]){
        List<Pet> pets = new ArrayList<Pet>();
        pets.add(new Cat());
        pets.add(new Dog());

        // 오류를 없애고 동작하게 하는 방법??
        Repository repository = new Repository(); // Test double  

        pets.forEach(pet ->{
            System.out.println("건강 " + pet.health);
            pet.repository = repository;
            pet.feed();

            try{
                pet.setHealth(100); //must fail
            }catch(Exception e){
                System.out.println("- 90 이상의 건강도를 입력하면 오류가 나야 함 - PASSED ");
            } 

            System.out.println("건강 " + pet.health);

            pet.save();
        });
    }
}
