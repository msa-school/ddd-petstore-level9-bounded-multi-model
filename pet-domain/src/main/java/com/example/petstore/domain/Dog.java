package com.example.petstore.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("dog")
public class Dog extends Pet implements Runnable {

    @Override
    public void speak() {
        System.out.println("멍멍");
    }

    @Override
    public void run() {
        System.out.println(" Dog Run!!!");
        
    }

    @Override
    public void eat() {
        
        setEnergy(getEnergy() + 2);

    }

    @Override
    public void sleep() {
        super.sleep();
      
        setAppearance(getAppearance() + 1);
        
    }

    
    
    
}
