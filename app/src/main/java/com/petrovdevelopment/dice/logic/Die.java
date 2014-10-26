package com.petrovdevelopment.dice.logic;

import com.petrovdevelopment.common.util.MathUtil;

/**
 * Created by andrey on 2014-10-18.
 */
public class Die {

    private int sidesCount;

    /**
     * Create a die with the given sides count (standard die is six)
     * @param sidesCount
     */
    public Die(int sidesCount) {
        this.sidesCount = sidesCount;
    }


    /**
     *
     * @return Roll the die and get one its sides. Should be usually random (0 indexed)
     */
    public int roll() {
        return MathUtil.getRandomUpTo(sidesCount - 1);
    }




}
