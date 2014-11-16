package com.petrovdevelopment.dice.drawables;

import android.view.View;

/**

 * Created by Andrey Petrov on 2014-11-15.
 */
public class DieSprite extends Sprite<Integer> {
    private int lastFrame = 0;

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
    public DieSprite(int x, int y, int currentFrame, int frameCount, int spriteResourceId, View parentView, int width, int height) {
        super(x, y, currentFrame, frameCount, spriteResourceId, parentView, width, height);
    }

    /**
     *  Advance the animation, switching to the side the die6 was rolled to, or just to the next side
    *
     * @param frame
     */
    @Override
    protected void changeFrame(Integer frame) {
        if (frame >= 0 && frame < frameCount) currentFrame = frame;
        else super.changeFrame(frame); //if frame is invalid use default implementation which ignores the parameter
    }


}
