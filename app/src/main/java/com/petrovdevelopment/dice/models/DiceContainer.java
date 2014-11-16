package com.petrovdevelopment.dice.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.petrovdevelopment.dice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * For now only integer side dice. TODO replace those by configurable by the user sides
 * <p/>
 * Created by Andrey Petrov on 2014-11-16.
 */
public class DiceContainer implements Parcelable {
    private static Class<?> SIDE_TYPE = Die.class;

    List<Die<Integer>> dice;

    public DiceContainer() {
        dice = new ArrayList<Die<Integer>>();
    }

    public static DiceContainer createDiceContainer() {
        return new DiceContainer();
    }

    /**
     * Add new dice to the dice container. Can be chained
     *
     * @param sidesCount
     * @return
     */
    public DiceContainer addDie(int sidesCount) {
        return addDie(sidesCount, getDefaultDieResource(sidesCount));
    }


    public DiceContainer addDie(int sidesCount, int resourceId) {
        List<Integer> sides = createIntegerSides(sidesCount);
        Die<Integer> die = new Die<Integer>(0, sides, Integer.class, resourceId);
        dice.add(die);
        return this;
    }

    public void setDice(List<Die<Integer>> dice) {
        this.dice = dice;
    }

    public List<Die<Integer>> getDice() {
        return dice;
    }

    /**
     * Util method to generate standard 1-indexed sides
     *
     * @param sidesCount
     */
    private static List<Integer> createIntegerSides(int sidesCount) {
        List<Integer> sides = new ArrayList<Integer>();
        for (int i = 0; i < sidesCount; i++) {
            sides.add(i + 1);
        }
        return sides;
    }

    /**
     * Helper method to return the approriate default die resource for the given number of sides.
     * TODO maybe create an enum with mapping between die sides and resource
     *
     * @param sidesCount
     * @return
     */
    private static int getDefaultDieResource(int sidesCount) {
        return R.drawable.die6;
    }


    /**
     * Helper method just for debugging, to verify randomness distribution over 100 rolls of every die
     * @return
     */
    public int[] testRandomness() {
        int[] indicesCounts = new int[6];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < dice.size(); j++) {
                int sideIndex = dice.get(j).roll().getCurrentSideIndex();
                indicesCounts[sideIndex]++;
            }
        }

        return indicesCounts;
    }


    //region Parcel implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dice);
    }

    private DiceContainer(Parcel in) {
        in.readList(this.dice, SIDE_TYPE.getClassLoader());
    }

    public static final Parcelable.Creator<DiceContainer> CREATOR = new Parcelable.Creator<DiceContainer>() {
        public DiceContainer createFromParcel(Parcel source) {
            return new DiceContainer(source);
        }

        public DiceContainer[] newArray(int size) {
            return new DiceContainer[size];
        }
    };
    //endregion
}
