package com.petrovdevelopment.dice.drawables;

import android.graphics.Canvas;

/**
 * All game elements implement this interface. This assures they can be manipulated from the game/animation loop.
 * The callbacks to be called from the loop are init() on start, update and render in the loop, and finish() on ending.
 * The loop checking condition is using both duration and isFinished to verify if the loop should go on
 * @author andrey
 *
 */
public interface Loopable<E> {

    /**
     * Initialize the loopable element.
     * The operation should be idempotent, meaning multiple calls to init should be the same as one call to it.
     * Should not be necessarily implemented. The constructor of the specific Loopable can do its job usually
     * Not thread safe. Should it be?
     */
    void init();

	/**
	 * Update the state of the game element. Can be relatively slower compared to render, but still try to keep it slim
	 */
	void update(E externalState);
	
	/**
	 * Render the game element on screen. Should be a fast operation.
	 * @param canvas
	 */
	void render(Canvas canvas);

    /**
     * Duration of the loop in miliseconds. -1 for endless
     * If the duration is -1 then the loop thread will stop only when the isFinished state becomes true
     * @return
     */
    long getDuration();

    /**
     * Whether the loopable is in finished state.
     * The loopable can calculate based on some internal changes that its state is finished, but this is not necessary.
     * The loop will use this property to check and cut the animation earlier than its duration if this at some point turns to true.
     * @return
     */
    boolean isFinished();

    /**
     * Call this when the loop is finished. The loop thread will check if a condition is met and will callback this method to finish the loop
     */
    void finish();
}
