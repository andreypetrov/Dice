package com.petrovdevelopment.dice.drawables;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Class holding all the information required to render sprite on the screen.
 * Touchable to be able to react to touches if needed.
 * Loopable to be able to interact with the game/animation loop.
 * Parceable so that it can restore its state, if the game stops
 *
 * @author andrey
 */
public class Sprite<E> implements Loopable<E>, Touchable, Parcelable {

    protected int frameCount;
    protected int currentFrame;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Rect src; // rectangle, part of the sprite bitmap
    protected Rect dst; // rectangle, part of the canvas
    protected int spriteResourceId;


    protected View parentView;
    protected Bitmap bitmap;

    /**
     * Create a sprite with a one row sprite sheet
     *
     * @param x
     * @param y
     * @param currentFrame
     * @param frameCount
     * @param spriteResourceId the id of the sprite sheet. Needs to be with one row and with frameCount columns
     * @param parentView
     * @param width  -1 to use native bitmap width divided by frame count
     * @param height -1 to use native bitmap height
     */
    public Sprite(int x, int y, int currentFrame, int frameCount, int spriteResourceId, View parentView, int width, int height) {

        this.parentView = parentView;
        this.frameCount = frameCount;
        this.spriteResourceId = spriteResourceId;

        bitmap = BitmapFactory.decodeResource(this.parentView.getResources(), this.spriteResourceId);
        initWidthAndHeight(width, height);
        bitmap = Bitmap.createScaledBitmap(bitmap, this.width*frameCount , this.height, false);


        this.x = x;
        this.y = y;
        this.currentFrame = currentFrame;
        preRender();
    }


    /**
     * Use the native bitmap height and width if non are provided
     */
    protected void initWidthAndHeight(int width, int height) {
        if (width == -1) this.width = bitmap.getWidth() / frameCount;
        else this.width = width;

        if (height == -1) this.height = bitmap.getHeight();
        else this.height = height;
    }



    /**
     * Sets up the game view the bitmap and some prerender calculations.
     * To be called after initialization via the Parcel constructor
     * Use this to restore state to the bitmap, after all the rest of the state has been restored.
     *
     * @param parentView
     */
    public void setParentViewAndInitialize(View parentView) {
        this.parentView = parentView;
        this.bitmap = BitmapFactory.decodeResource(this.parentView.getResources(), this.spriteResourceId);
        preRender();
    }


    /**
     * Prepare for rendering, relatively expensive, because it creates object
     */
    protected void preRender() {
        int srcX = currentFrame * width;
        int srcY = 0;

        // preallocate objects to avoid creating any new object in the onDraw() method
        src = new Rect(srcX, srcY, srcX + width, srcY + height);
        dst = new Rect(x, y, x + width, y + height);
    }

    /**
     * Advance the animation, creating illusion of walking. increment should be first so that in the onDraw() we have
     * currentFrame = 0/1/2 Starting from the second frame.
     */
    protected void changeFrame(E notUsed) {
        currentFrame++;
        currentFrame = currentFrame % frameCount;
    }

    /**
     * Derived field
     *
     * @return
     */
    public float getCenterX() {
        return x + (width / 2);
    }

    /**
     * Derived field
     *
     * @return
     */
    public float getCenterY() {
        return y + (height / 2);
    }

    //region Getters and Setters
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


    public int getFrameCount() {
        return frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public View getParentView() {
        return parentView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Rect getSrc() {
        return src;
    }

    public Rect getDst() {
        return dst;
    }

    public int getSpriteResourceId() {
        return spriteResourceId;
    }
    //endregion

    //region Touchable implementation
    @Override
    public boolean isTouched(float touchX, float touchY) {
        return ((touchX >= x && touchX <= x + width) && (touchY >= y && touchY <= y + height));
    }

    @Override
    public void onTouch() {
    }
    //endregion

    //region Loopable implementation
    @Override
    public void init() {
    }

    @Override
    public void update(E notUsed) {
        changeFrame(notUsed);
        preRender();
    }

    @Override
    public void render(Canvas canvas) {
        //U.log(this, "rendering on canvas");
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    @Override
    public boolean isFinished() {
        return false; //never finished
    }

    @Override
    public void finish() {
        //do nothing
    }
    //endregion

    //region Parceler Implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.frameCount);
        dest.writeInt(this.currentFrame);
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.spriteResourceId);
    }

    private Sprite(Parcel in) {
        this.frameCount = in.readInt();
        this.currentFrame = in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.spriteResourceId = in.readInt();
    }

    public static final Parcelable.Creator<Sprite> CREATOR = new Parcelable.Creator<Sprite>() {
        public Sprite createFromParcel(Parcel source) {
            return new Sprite(source);
        }

        public Sprite[] newArray(int size) {
            return new Sprite[size];
        }
    };
    //endregion
}
