package com.petrovdevelopment.dice.models;

/**
 * A side of a die.
 * Currently it just has a name. In the future we may add more to it, but this is ok for now
 * Created by Andrey Petrov on 2014-11-17.
 */
public class Side {
    public String name;

    public Side(String name) {
        this.name = name;
    }

    /**
     * Copy constructor
     * @param original
     */
    public Side(Side original) {
        this.name = original.name;
    }
}
