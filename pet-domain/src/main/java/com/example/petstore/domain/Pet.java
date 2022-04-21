package com.example.petstore.domain;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(
    discriminatorType = DiscriminatorType.STRING,
    name = "pet_type",
    columnDefinition = "CHAR(5)"
)
public abstract class Pet {     // Entity. Domain Class.


    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;    
        public Long getId() {
            return id;
        }

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
            if(name==null) throw new IllegalArgumentException("이름은 꼭 들어가야 합니다");
            this.name = name;
        }

    String type;
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }


    abstract public void speak();

    @PostPersist
    public void onPostPersist(){
        PetReserved petReserved = new PetReserved();
        petReserved.setAppearance(this.getAppearance());
        petReserved.setEnergy(this.getEnergy());
        petReserved.setId(this.getId());
        petReserved.setName(this.getName());
        petReserved.setType(this.getType());
        petReserved.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){

        PetUpdated petUpdated = new PetUpdated();
        petUpdated.setAppearance(this.getAppearance());
        petUpdated.setEnergy(this.getEnergy());
        petUpdated.setId(this.getId());
        petUpdated.setName(this.getName());
        petUpdated.setType(this.getType());

        petUpdated.publishAfterCommit();

    }
        
    // List<Listener> listeners = new ArrayList<Listener>();
    // public void addListener(Listener listener) {
    //     this.listeners.add(listener);
    // }

    private int energy = 0;
        public int getEnergy() {
            return energy;
        }
        protected void setEnergy(int energy) {
            if(Math.abs(this.energy - energy) < 3 )
                this.energy = energy;
            else    
                throw new IllegalArgumentException("Energy change is too big");
        }

    public void eat(){
        energy += 1;

        // if(listeners!=null){
        //     for(int i = 0; i<listeners.size(); i++){
        //         listeners.get(i).energyChanged(energy);
        //     }
        // }
    }

    public void sleep(){
        energy += 2;
    }

    @Override
    public String toString() {
        
        return "<a href='./"+this.getClass().getSimpleName().toLowerCase()+"'" + ">" + this.getClass().getSimpleName() + "</a>";
    }
    
}
