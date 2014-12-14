package com.petrovdevelopment.dice.models;

import com.petrovdevelopment.common.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A die.
 * To create an unbalanced die, just add the same side more than once to it (or a copy of the same side?)
 * All members public because of the ugly getter and setter java standards
 * Created by Andrey Petrov on 2014-11-17.
 */
public class CustomDie implements Model {
    public int currentSideIndex;
    public String name; //optional
    public List<Side> sides;
    //if sprite name is not available or the image is not on the disc, then sprite id will be used
    public String spriteName;
    public int spriteResourceId;

    /**
     * Convenience builder class. To avoid messy long constructor parameters
     */
    public static class Builder {
        private CustomDie die;

        public Builder() {
            die = new CustomDie();
        }

        public static Builder newInstance () {
            return new Builder();
        }

        public Builder name(String name) {
            die.name = name;
            return this;
        }


        public Builder side(Side side) {
            die.sides.add(side);
            return this;
        }

        public Builder side(String name) {
            Side side = new Side(name);
            die.sides.add(side);
            return this;
        }

        public Builder sides(String[] sideNames) {
            for (String sideName : sideNames) {
                Side side = new Side(sideName);
                die.sides.add(side);
            }
            return this;
        }

        /**
         * Simple way to generate sides from 1 to diceSize, included
         * @param diceSize
         * @return
         */
        public Builder sides(int diceSize) {
            List<Side> sides = new ArrayList<Side>();
            for (int i = 1; i <= diceSize; i++) {
                Side side = new Side(String.valueOf(i));
                die.sides.add(side);
            }
            return this;
        }

        public Builder sides(List<String> sideNames) {
            for (String sideName : sideNames) {
                Side side = new Side(sideName);
                die.sides.add(side);
            }
            return this;
        }

        public Builder copyOf(CustomDie other) {
            die.name = other.name;
            die.spriteName = other.spriteName;
            die.spriteResourceId = other.spriteResourceId;
            die.currentSideIndex = other.currentSideIndex;
            for (Side originalSide : other.sides) {
                Side copySide = new Side(originalSide);
                die.addSide(copySide);
            }
            return this;
        }

        public Builder spriteName(String spriteName) {
            die.spriteName = spriteName;
            return this;
        }


        public Builder spriteResourceId(int spriteResourceId) {
            die.spriteResourceId = spriteResourceId;
            return this;
        }


        public CustomDie build() {
            return die;
        }

    }

    public CustomDie() {
    }

    /**
     * Copy constructor
     * @param other
     */
    public CustomDie(CustomDie other) {
        this.name = other.name;
        this.spriteName = other.spriteName;
        this.spriteResourceId = other.spriteResourceId;
        this.currentSideIndex = other.currentSideIndex;
        for (Side originalSide : other.sides) {
            Side copySide = new Side(originalSide);
            this.addSide(copySide);
        }
    }

    public int getSize() {
        return sides.size();
    }


    public String getName() {
        return name;
    }

    public CustomDie addSide(Side side) {
        sides.add(side);
        return this;
    }

    public Side removeSide(int index) {
        Side result = sides.remove(index);
        return result;
    }

    public int roll() {
        currentSideIndex = MathUtil.nextInt(getSize());
        return currentSideIndex;
    }

    public Side getCurrentSide() {
        return sides.get(currentSideIndex);
    }

    @Override
    public void save() {

    }
}
