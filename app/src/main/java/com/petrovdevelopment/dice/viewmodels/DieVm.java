package com.petrovdevelopment.dice.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.petrovdevelopment.dice.drawables.Sprite;
import com.petrovdevelopment.dice.logic.Die;

/**
 * A view model that holds the model and the sprite, representing it on the screen
 * Created by Andrey Petrov on 2014-11-09.
 */
public class DieVm<E> implements Parcelable {

    private Die<E> die;
    private Sprite dieSprite;

    public DieVm(View parentView, Die<E> die, int spriteResourceId, int startX, int startY) {
        this.die = die;
        this.dieSprite = new Sprite(startX, startY, die.getCurrentSideIndex(), die.getSideCount(), spriteResourceId, parentView);
    }

    public Die<E> getDie() {
        return die;
    }

    public Sprite getSprite() {
        return dieSprite;
    }






    //region Parceler Implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.die, 0);
        dest.writeParcelable(this.dieSprite, 0);
    }

    private DieVm(Parcel in) {
        this.die = in.readParcelable(Die.class.getClassLoader());
        this.dieSprite = in.readParcelable(Sprite.class.getClassLoader());
    }

    public static final Parcelable.Creator<DieVm> CREATOR = new Parcelable.Creator<DieVm>() {
        public DieVm createFromParcel(Parcel source) {
            return new DieVm(source);
        }

        public DieVm[] newArray(int size) {
            return new DieVm[size];
        }
    };
    //endregion
}
