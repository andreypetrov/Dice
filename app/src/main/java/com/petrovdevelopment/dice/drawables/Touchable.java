package com.petrovdevelopment.dice.drawables;

public interface Touchable {
	/**
	 * Checks if the passed parameters are within the boarders of the object
	 * @param touchX
	 * @param touchY
	 * @return true if the object is touched
	 */
	boolean isTouched(float touchX, float touchY);

	/**
	 * Produce some effect when the object is touched
	 */
	void onTouch();
}
