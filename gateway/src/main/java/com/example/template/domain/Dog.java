package com.example.template.domain;

public class Dog extends Pet{

    @Override
    public void feed() {
        health += 2;
    }

}
