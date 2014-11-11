package com.petrovdevelopment.dice.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 2014-10-18.
 */
public class Game implements Parcelable {
    private Die<Integer> die;
    private int lastResult;

    public Game() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        List<Integer> sides = Arrays.asList(array);
        die = new Die<Integer>(0, sides, Integer.class);

    }

    public void rollDice() {
        die.roll();
        lastResult = (Integer) die.getCurrentSide();

    }

    public int getDiceResult() {
        return lastResult;
    }


    //region Parceler Implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.die, 0);
        dest.writeInt(this.lastResult);
    }

    private Game(Parcel in) {
        this.die = in.readParcelable(Die.class.getClassLoader());
        this.lastResult = in.readInt();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    //endregion
}
