package com.petrovdevelopment.dice.logic;

import android.graphics.Canvas;

import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.dice.models.DiceContainer;
import com.petrovdevelopment.dice.threads.AnimationThread;
import com.petrovdevelopment.dice.threads.GameSurfaceView;

import java.util.List;

/**
 * Created by andrey on 2014-10-18.
 */
public class GameController {
    private World world;
    private AnimationThread animationThread;

    private static int INVALID_FRAME_INDEX = -1;
    private static int ROW_DURATION_MILLIS = 3000;
    private static int FPS = 8;

    private int lastResult;
    private GameSurfaceView gameSurfaceView;

    private GameController(GameSurfaceView gameSurfaceView) {
        this.gameSurfaceView = gameSurfaceView;
        DiceContainer diceContainer = DiceContainer.createDiceContainer().addDie(6).addDie(6).addDie(6);
        this.world = World.createWorld(gameSurfaceView, diceContainer);
    }

    public static GameController createGame(GameSurfaceView gameSurfaceView) {
        return new GameController(gameSurfaceView);
    };



    //region Game interface

    /**
     * Roll the die
     */
    private void startRollThread() {
        animationThread = new AnimationThread(FPS, ROW_DURATION_MILLIS, this);
        animationThread.start();
 }

    public void rollDice() {
        world.init();
        startRollThread();
    }

    public List<Integer> getDiceResult() {
        return world.getDiceResults();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    //endregion




    //region LoopInterface

    /**
     * Clear background. Thread safe, locked on the surface holder
     */
    public void clear() {
        Canvas canvas = null;
        try {
            canvas = gameSurfaceView.getHolder().lockCanvas();
            // lockCanvas will return null in the last run, when we are destroying the view
            if (canvas != null) {
                synchronized (gameSurfaceView.getHolder()) {
                   world.drawBackground(canvas);
                }
            }

        } finally {
            if (canvas != null) {
                gameSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }

    }

    /**
     * Update + render. Thread safe, locked on the surface holder
     * Update and render are synchronized to happen after each other!
     */
    public void onAnimationStep() {
       onAnimationStep(false);
    }



    public void onAnimationStep(boolean useRealDiceIndices) {
        Canvas canvas = null;
        try {
            canvas = gameSurfaceView.getHolder().lockCanvas();
            // lockCanvas will return null in the last run, when we are destroying the view
            if (canvas != null) {
                synchronized (gameSurfaceView.getHolder()) {
                    //TODO: maybe add one initial update and draw before the while loop to have a proper starting state
                    world.update(useRealDiceIndices); //
                    world.render(canvas);
                }
            }

        } finally {
            if (canvas != null) {
                gameSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }


    public boolean isFinished() {
        return world.isFinished();
    }

    public void onAnimationFinish() {
        onAnimationStep(true);
        U.log(this, "ANIMATION FINISHED with results: " + world.getDiceResults());
    }
    //endregion
}
