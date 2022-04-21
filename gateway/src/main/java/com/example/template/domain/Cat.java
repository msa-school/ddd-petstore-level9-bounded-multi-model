package com.example.template.domain;

public class Cat extends Pet{

    @Override
    public void feed() {
        super.feed();

        health += 1;
    }

    
}
