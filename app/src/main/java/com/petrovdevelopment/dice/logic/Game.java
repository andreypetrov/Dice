package com.petrovdevelopment.dice.logic;

import android.graphics.Canvas;

import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.dice.threads.DieRollThread;
import com.petrovdevelopment.dice.threads.GameSurfaceView;

/**
 * Created by andrey on 2014-10-18.
 */
public class Game {
    private World world;
    private DieRollThread dieRollThread;

    private int lastResult;
    private GameSurfaceView gameSurfaceView;

    public Game(GameSurfaceView gameSurfaceView) {
        this.gameSurfaceView = gameSurfaceView;
        int rowDurationInMilliseconds = 3000; //3 sec
        this.world = World.createWorld(gameSurfaceView, rowDurationInMilliseconds);



    }

    public static Game createGame(GameSurfaceView gameSurfaceView) {
        return new Game(gameSurfaceView);
    };


    /**
     * Clear background
     */
    public void clear() {
            Canvas canvas = gameSurfaceView.getHolder().lockCanvas();
            synchronized (gameSurfaceView.getHolder()) {
                world.drawBackground(canvas);
            }
            gameSurfaceView.getHolder().unlockCanvasAndPost(canvas);
    }

    public World getWorld() {
       return world;
    }

    /**
     * Roll the die
     */
    private void startRollThread() {
        dieRollThread = new DieRollThread(2, gameSurfaceView, world, new DieRollThread.OnFinishCallback() {
            @Override
            public void onFinish() {
                lastResult = world.getDiceResult();
                U.log(this, "FINISHED THREAD with die result: " + lastResult);
            }
        });
        dieRollThread.start();
 }

    public void rollDice() {
        startRollThread();
    }

    public int getDiceResult() {
        return world.getDiceResult();
    }
}
