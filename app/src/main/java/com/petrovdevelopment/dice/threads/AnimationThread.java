package com.petrovdevelopment.dice.threads;

import com.petrovdevelopment.dice.logic.GameController;

/**
 * Game loop thread.
 * 
 * @author andrey
 * 
 */
public class AnimationThread extends Thread {
	private GameController gameController; //gameController can be replaced by an interface for looser coupling

	private volatile boolean running = true;
    private long initTime = 0;
    private int stateVariableToIgnore = 0; //just because gameController accepts variable

    private long fps;           //frames per second
    private long frameDuration; //a single frame duration in milliseconds
    private long animationDuration;

    /**
     * Create a new thread.
     * @param fps - The loop will try to achieve this many iterations(frames) per second.
     *              The higher the number, the faster will the loop go but there is a risk the hardware will not be able to achieve it.
     *              If it is not important don't go more than 30
     * @param gameController  the main object to be updated and rendered
     */
	public AnimationThread(long fps, long animationDuration, GameController gameController) {
		this.gameController = gameController;
        this.fps = fps;
        this.frameDuration = 1000/fps;
        this.animationDuration = animationDuration;
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
        initTime = System.currentTimeMillis();
		while (running) {
			long loopStartTime = System.currentTimeMillis();
            gameController.onAnimationStep();
			sleepIfNeededToSlowDownLoop(loopStartTime);
			checkConditionsToEndLoop();
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
     * Check if the duration has passed or the isFinished flag on the gameController has been raised
     */
    private void checkConditionsToEndLoop() {
        long duration  = System.currentTimeMillis() - initTime;
        if(gameController.isFinished() || duration > animationDuration) {
            setRunning(false); //stop the loop
            gameController.onAnimationFinish(); //call the gameController lifecycle callback
        }
    }

}
