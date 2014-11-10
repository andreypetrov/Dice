package com.petrovdevelopment.dice.logic;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 2014-10-18.
 */
public class Game {
    private Die die;
    private int lastResult;

    public Game() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        List<Integer> sides = Arrays.asList(array);
        die = new Die<Integer>(0, sides);

    }

    public void rollDice() {
        die.roll();
        lastResult = (Integer) die.getCurrentSide();

    }

    public int getDiceResult() {
        return lastResult;
    }
}
