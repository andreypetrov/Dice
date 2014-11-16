package com.petrovdevelopment.dice.threads;

import android.graphics.Canvas;

import com.petrovdevelopment.dice.drawables.Loopable;

/**
 * Game loop thread.
 * 
 * @author andrey
 * 
 */
public class DieRollThread extends Thread {
	private GameSurfaceView gameView;
	private Loopable loopable;
    private OnFinishCallback onFinishCallback;

    public interface OnFinishCallback {
        public void onFinish();
    }

	private volatile boolean running = true;
    private long initTime = 0;
    private int stateVariableToIgnore = 0; //just because loopable accepts variable

    private long fps;           //frames per second
    private long frameDuration; //a single frame duration in milliseconds

    /**
     * Create a new thread.
     * @param fps - The loop will try to achieve this many iterations(frames) per second.
     *              The higher the number, the faster will the loop go but there is a risk the hardware will not be able to achieve it.
     *              If it is not important don't go more than 30
     * @param gameView this is where the loopable will be rendered into
     * @param loopable  the main object to be updated and rendered
     * @param onFinishCallback called at the end of the thread execution
     */
	public DieRollThread(long fps, GameSurfaceView gameView, Loopable<Integer> loopable, OnFinishCallback onFinishCallback) {
		this.gameView = gameView;
		this.loopable = loopable;
        this.onFinishCallback = onFinishCallback;
        this.fps = fps;
        this.frameDuration = 1000/fps;
	}

    /**
     * Stop or start the thread execution. The private variable is volatile and so this method is thread-safe
     * @param running
     */
	public void setRunning(boolean running) {
		this.running = running;
	}

	//TODO add one rendering before the loop and after the initial setup
	@Override
	public void run() {
        loopable.init();
        initTime = System.currentTimeMillis();

		while (running) {
			long loopStartTime = System.currentTimeMillis();
            updateAndRender();
			sleepIfNeededToSlowDownLoop(loopStartTime);
			checkConditionsToEndLoop();
		}
	}


    /**
     * Update and render should be synchronized to happen after each other!
     */
    private void updateAndRender() {
        Canvas canvas = null;
        try {
            canvas = gameView.getHolder().lockCanvas();
            // lockCanvas will return null in the last run, when we are destroying the view
            if (canvas != null) {
                synchronized (gameView.getHolder()) {
                    //TODO: maybe add one initial update and draw before the while loop to have a proper starting state
                    loopable.update(stateVariableToIgnore); //
                    loopable.render(canvas);
                }
            }

        } finally {
            if (canvas != null) {
                gameView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }


    private void sleepIfNeededToSlowDownLoop(long loopStartTime) {
        long loopDuration = System.currentTimeMillis() - loopStartTime;
        long loopSleepTime = frameDuration - loopDuration;
        try {
            if (loopSleepTime > 0) { // slow down if it is too fast
                sleep(loopSleepTime);
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Check if the duration has passed or the isFinished flag on the loopable has been raised
     */
    private void checkConditionsToEndLoop() {
        long duration  = System.currentTimeMillis() - initTime;
        if(loopable.isFinished() || duration > loopable.getDuration()) {
            setRunning(false); //stop the loop
            loopable.finish(); //call the loopable lifecycle callback
            if (onFinishCallback!=null) onFinishCallback.onFinish(); //call if there is a special callback passed to this thread
        }
    }

}
