package com.example.petstore.domain;

import javax.persistence.*;


@Entity
public class Health {

    @Id @GeneratedValue
    Long id;

    int bloodPressure;
        public int getBloodPressure() {
            return bloodPressure;
        }
        public void setBloodPressure(int bloodPressure) {
            this.bloodPressure = bloodPressure;
        }
    

    public boolean checkHealthy(){
        if(getBloodPressure() < 120 && getBloodPressure() > 80) return true;

        return false;
    }
    
}
