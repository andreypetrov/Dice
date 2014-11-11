package com.petrovdevelopment.dice.logic;


import android.os.Parcel;
import android.os.Parcelable;

import com.petrovdevelopment.common.util.MathUtil;

import java.util.List;

/**
 * A die model. Has number of sides and a current side. Every side also can be represented by an object, but not necessarily
 * Created by andrey on 2014-10-18.
 * TODO use a library for parcelizing the data, checkout AutoParcel vs Parceler
 *https://github.com/frankiesardo/auto-parcel
 * https://github.com/johncarl81/parceler
 *
 */
public class Die<E> implements Parcelable {

    int currentSideIndex;
    Class<?> sideType;
    List<E> sides;



    /**
     * Create a die with the given sides count (standard die is six)
     *
     * @param currentSideIndex
     * @param sides list of side objects
     */
    public Die(int currentSideIndex, List<E> sides, Class<?> sideType) {
        this.currentSideIndex = currentSideIndex;
        this.sideType = sideType;
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


    //region Parceler Implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.currentSideIndex);
        dest.writeSerializable(this.sideType);
        dest.writeList(this.sides);
    }

    private Die(Parcel in) {
        this.currentSideIndex = in.readInt();
        this.sideType = (Class<?>) in.readSerializable();
        in.readList(this.sides, this.sideType.getClassLoader());
    }

    public static final Creator<Die> CREATOR = new Creator<Die>() {
        public Die createFromParcel(Parcel source) {
            return new Die(source);
        }

        public Die[] newArray(int size) {
            return new Die[size];
        }
    };

    //endregion
}
