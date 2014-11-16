package com.petrovdevelopment.dice.viewmodels;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.petrovdevelopment.dice.drawables.DieSprite;
import com.petrovdevelopment.dice.drawables.Loopable;
import com.petrovdevelopment.dice.logic.Die;

/**
 * A view model that holds the model and the sprite, representing it on the screen
 * Created by Andrey Petrov on 2014-11-09.
 */
public class DieVm<E> implements Parcelable, Loopable<Integer> {

    private Die<E> die;
    private DieSprite dieSprite;
    private long duration;

    /**
     * Create a Die view model with a given die, sprite data and getDuration of animation data.
     * To be used by the animation loop
     *
     * @param parentView
     * @param die
     * @param spriteResourceId
     * @param startX
     * @param startY
     * @param duration
     */
    public DieVm(View parentView, Die<E> die, int spriteResourceId, int startX, int startY, long duration) {
        this.die = die;
        this.dieSprite = new DieSprite(startX, startY, die.getCurrentSideIndex(), die.getSideCount(), spriteResourceId, parentView);
        this.duration = duration;
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
        dest.writeLong(this.duration);
    }

    private DieVm(Parcel in) {
        this.die = in.readParcelable(Die.class.getClassLoader());
        this.dieSprite = in.readParcelable(DieSprite.class.getClassLoader());
        this.duration = in.readLong();
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
    @Override
    public void init() {
    }

    /**
     * Todo: rewrite it to pass the sideIndex externally
     * @param notused
     */
    @Override
    public void update(Integer notused) {
        int sideIndex = getDie().roll().getCurrentSideIndex();
        getDieSprite().update(sideIndex);
    }

    @Override
    public void render(Canvas canvas) {
        getDieSprite().render(canvas);
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void finish() {
        getDieSprite().finish();
    }
    //endregion
}
