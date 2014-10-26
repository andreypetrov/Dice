package com.petrovdevelopment.dice.logic;

/**
 * Created by andrey on 2014-10-18.
 */
public class Game {
    private Die die;
    private int lastResult;

    public Game() {
        die = new Die(6);
    }

    public void rollDice() {
        lastResult = die.roll();

    }

    public int getDiceResult() {
        return lastResult;
    }
}
