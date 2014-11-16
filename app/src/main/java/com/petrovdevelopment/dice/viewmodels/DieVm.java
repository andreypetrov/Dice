package com.petrovdevelopment.dice.viewmodels;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.petrovdevelopment.dice.drawables.DieSprite;
import com.petrovdevelopment.dice.drawables.Loopable;
import com.petrovdevelopment.dice.models.Die;

/**
 * A view model that holds the model and the sprite, representing it on the screen
 * Created by Andrey Petrov on 2014-11-09.
 */
public class DieVm<E> implements Parcelable, Loopable<Integer> {

    private Die<E> die;
    private DieSprite dieSprite;

    /**
     * Create a Die view model with a given die6, sprite data and getDuration of animation data.
     * To be used by the animation loop
     *
     * @param parentView
     * @param die
     * @param startX
     * @param startY
     */
    public DieVm(View parentView, Die<E> die, int startX, int startY, int width, int height) {
        this.die = die;
        this.dieSprite = new DieSprite(startX, startY, die.getCurrentSideIndex(), die.getSideCount(), die.getSpriteResourceId(), parentView, width, height);
    }


    public Die<E> getDie() {
        return die;
    }

    public DieSprite getDieSprite() {
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
        this.dieSprite = in.readParcelable(DieSprite.class.getClassLoader());
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


    //region Looper implementation

    /**
     * Roll the die6 only once in the beginning of the animation loop
     */
    @Override
    public void init() {
       getDie().roll();
    }

    /**
     * TODO: rewrite it to pass the sideIndex externally
     * @param frameIndex
     */
    @Override
    public void update(Integer frameIndex) {
        getDieSprite().update(frameIndex);
    }

    @Override
    public void render(Canvas canvas) {
        getDieSprite().render(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * Update the sprite to the correct side at the end of the animation
     */
    @Override
    public void finish() {
//        int sideIndex = getDie().getCurrentSideIndex();
//        getDieSprite().update(sideIndex);
//        getDieSprite().render();
    }
    //endregion
}
