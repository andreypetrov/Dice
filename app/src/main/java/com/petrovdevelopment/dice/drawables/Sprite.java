package com.petrovdevelopment.dice.drawables;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.petrovdevelopment.dice.viewmodels.SpriteVm;

import java.util.Random;

/**
 * Class representing a single npc. Parceable so that it can restore its state, if the game stops
 *
 * @author andrey
 *
 */
public class Sprite implements Agent, Touchable {

    protected int frameCount;
    protected int currentFrame;

    protected View parentView;

    protected Bitmap bitmap;

    protected int x;
    protected int y;

    protected int width;
    protected int height;


    protected Rect mSrc; // rectangle, part of the sprite bitmap
    protected Rect mDst; // rectangle, part of the canvas

    protected SpriteVm spriteVm;

    /**
     * Use this constructor when creating dice for a new game.
     * @param x
     * @param y
     * @param currentFrame
     * @param parentView
     * @param spriteVm
     */
    public Sprite(int x, int y, int currentFrame, View parentView, SpriteVm spriteVm) {

        this.parentView = parentView;
        spriteVm = spriteVm;
        Random random = new Random();
        frameCount = spriteVm.getFrameCount();
        bitmap = BitmapFactory.decodeResource(this.parentView.getResources(), spriteVm.getSpriteResourceId());

        width = bitmap.getWidth() / frameCount;
        height = bitmap.getHeight();

        this.x = x;
        this.y = y;
        this.currentFrame = currentFrame;
        preRender();
    }



    /**
     * Advance the animation, creating illusion of walking. increment should be first so that in the onDraw() we have
     * currentFrame = 0/1/2 Starting from the second frame.
     */
    protected void changeFrame() {
        currentFrame++;
        currentFrame = currentFrame % frameCount;
    }





    /**
     * Prepare for rendering, relatively expensive
     */
    protected void preRender() {
        int srcX = currentFrame * width;
        int srcY = 0;

        // preallocate objects to avoid creating any new object in the onDraw() method
        mSrc = new Rect(srcX, srcY, srcX + width, srcY + height);
        mDst = new Rect(x, y, x + width, y + height);
    }

    @Override
    public void update() {
        changeFrame();
        preRender();
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(bitmap, mSrc, mDst, null);
    }

    @Override
    public boolean isTouched(float touchX, float touchY) {
        return ((touchX >= x && touchX <= x + width) && (touchY >= y && touchY <= y + height));
    }

    @Override
    public void touch() {
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpriteVm getSpriteVm() {
        return spriteVm;
    }

    public float getCenterX() {
        return x + (width / 2);
    }

    public float getCenterY() {
        return y + (height / 2);
    }


}
