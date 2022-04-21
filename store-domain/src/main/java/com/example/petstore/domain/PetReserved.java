package com.example.petstore.domain;

import com.example.petstore.AbstractEvent;

public class PetReserved extends AbstractEvent {
    Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}

    private int appearance;
    public int getAppearance() {
        return appearance;
    }
    protected void setAppearance(int appearance) {
        this.appearance = appearance;
    }


    String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    private int energy = 0;
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
