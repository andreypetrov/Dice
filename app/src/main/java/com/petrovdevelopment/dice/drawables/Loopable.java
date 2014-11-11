package com.petrovdevelopment.dice.drawables;

import android.graphics.Canvas;

/**
 * All game elements implement this interface. This assures they can be manipulated from the game loop.
 *
 * @author andrey
 *
 */
public interface Loopable {

    /**
     * Initialize the game element.
     * The operation should be idempotent, meaning multiple calls to init should be the same as one call to it.
     * Should not be necessarily implemented. The constructor of the specific Loopable can do its job usually
     */
    void init();

	/**
	 * Update the state of the game element. Can be relatively slower compared to render, but still try to keep it slim
	 */
	void update();
	
	/**
	 * Render the game element on screen. Should be a fast operation.
	 * @param canvas
	 */
	void render(Canvas canvas);
}
