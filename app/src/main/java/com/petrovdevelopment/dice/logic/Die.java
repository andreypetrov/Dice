package com.petrovdevelopment.dice.logic;

import com.petrovdevelopment.common.util.MathUtil;

import java.util.List;

/**
 * A die model. Has number of sides and a current side. Every side also can be represented by an object, but not necessarily
 * Created by andrey on 2014-10-18.
 */
public class Die<E> {

    private List<E> sides;
    private int currentSideIndex;

    /**
     * Create a die with the given sides count (standard die is six)
     *
     * @param currentSideIndex
     * @param sides list of side objects
     */
    public Die(int currentSideIndex, List<E> sides) {
        this.currentSideIndex = currentSideIndex;
        this.sides = sides;
    }

    public int getSideCount() {
        return sides.size();
    }

    /**
     * @return Roll the die and to change the current side index. Should be usually random (0 indexed)
     */
    public void roll() {
        currentSideIndex = MathUtil.getRandomUpTo(getSideCount() - 1);
    }

    public E getCurrentSide() {
        return sides.get(currentSideIndex);
    }

    public E getSide(int index) {
        return sides.get(index);
    }


    public int getCurrentSideIndex() {
        return currentSideIndex;
    }
    public void setCurrentSideIndex(int value) {
        currentSideIndex = value;
    }



}
