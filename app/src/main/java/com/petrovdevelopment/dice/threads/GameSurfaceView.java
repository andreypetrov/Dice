package com.petrovdevelopment.dice.threads;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * The surface on which the game world is being rendered.
 *
 * @author andrey
 */
public class GameSurfaceView extends SurfaceView {
    private GameSurfaceViewCallback gameSurfaceViewCallback;
    private SurfaceHolder holder;

    public interface GameSurfaceViewCallback extends SurfaceHolder.Callback {
        void onTouch(float x, float y);
    }

    public GameSurfaceView(Context context) {
        super(context);
        initialize();
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GameSurfaceView(Context context, GameSurfaceViewCallback callback) {
        super(context);
        this.gameSurfaceViewCallback = callback;
        initialize();
    }

    public GameSurfaceView(Context context, GameSurfaceViewCallback callback, AttributeSet attrs) {
        super(context, attrs);
        this.gameSurfaceViewCallback = callback;
        initialize();
    }


    public void setGameSurfaceViewCallback(GameSurfaceViewCallback callback) {
        this.gameSurfaceViewCallback = callback;
        initialize();
    }

    /**
     * Assign the gameSurfaceViewCallback to handle the callbacks of the view's lifecycle (obviously)
     */
    private void initialize() {
        if (gameSurfaceViewCallback != null) {
            holder = getHolder();
            holder.addCallback(gameSurfaceViewCallback);
        }
    }

    /**
     * Delegate to the gameSurfaceViewCallback the touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Make sure the touch event can happen only on new touch and not on persisting the touch
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // synchronize to make sure no sprite removal is happening during rendering
            synchronized (getHolder()) {
                gameSurfaceViewCallback.onTouch(event.getX(), event.getY());
            }
        }
        return true; // it is more efficient to pass true here to stop handling the event
    }
}
