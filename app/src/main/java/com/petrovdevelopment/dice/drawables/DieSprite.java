package com.petrovdevelopment.dice.drawables;

import android.view.View;

import com.petrovdevelopment.common.util.U;

/**
 * Created by Andrey Petrov on 2014-11-15.
 */
public class DieSprite extends Sprite<Integer> {


    /**
     * Create a sprite with a one row sprite sheet
     *
     * @param x
     * @param y
     * @param currentFrame
     * @param frameCount
     * @param spriteResourceId the id of the sprite sheet. Needs to be with one row and with frameCount columns
     * @param parentView
     */
    public DieSprite(int x, int y, int currentFrame, int frameCount, int spriteResourceId, View parentView) {
        super(x, y, currentFrame, frameCount, spriteResourceId, parentView);
    }


    //region Loopable implementation
    @Override
    public void update(Integer sideIndex) {
        U.log(this, "update with index: " + sideIndex);
        changeFrame(sideIndex);
        preRender();
    }

    /**
     *  Advance the animation, switching to the side the die was rolled to
    *
     * @param frame
     */
    protected void changeFrame(int frame) {
        currentFrame = frame;
    }


}
