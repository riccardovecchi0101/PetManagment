package com.example.petmanagment.ui.Customers.Pet;

import androidx.annotation.NonNull;

public class Pet {

    private String name, race, typology;
    private String UUID;


    public Pet(String s, String toString, String string, String s1) {
        this.name = null;
        this.race = null;
        this.typology = null;
        this.UUID = null;

    }

    public Pet(String name, String race, String typology) {
        this.name = name;
        this.race = race;
        this.typology = typology;
        this.UUID = UUID;

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

    public String getUUID() {
        return this.UUID;
    }


    @NonNull
    public String toString() {
        return this.name + this.race + this.typology + this.UUID;
    }


}
