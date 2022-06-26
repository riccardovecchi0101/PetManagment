package com.example.petmanagment.ui.Customers.Pet;

import androidx.annotation.NonNull;

public class Pet {

    private String name, race, typology;

    public Pet() {
        this.name = null;
        this.race = null;
        this.typology = null;
    }

    public Pet(String name, String race, String typology) {
        this.name = name;
        this.race = race;
        this.typology = typology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }


    @NonNull
    public String toString() {
        return this.name + this.race + this.typology;
    }


}
