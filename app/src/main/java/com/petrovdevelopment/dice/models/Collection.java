package com.petrovdevelopment.dice.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 2014-11-17.
 */
public class Collection {
    public String name;
    public List<CustomDie> dice;


    public Collection() {
    }

    public Collection(Collection other) {
        this.name = other.name;
        List<CustomDie> dice = new ArrayList<CustomDie>();
        for (CustomDie otherDie : other.dice) {
            CustomDie copy = new CustomDie(otherDie);
            dice.add(copy);
        }
    }

    public int getSize() {
        return dice.size();
    }

}
