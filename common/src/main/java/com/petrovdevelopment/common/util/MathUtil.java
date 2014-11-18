package com.petrovdevelopment.common.util;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class MathUtil {
	// latitude is in [-85, 85], but for street view purposes [-48, 71];
	// longitude is in [-180, 180]. It would be nice to slice out all the ocean areas, but let's see if it is needed

	public static final double MIN_LAT = -48.0d;
	public static final double MAX_LAT = 71.0d;
	public static final double MIN_LNG = -180.0d;
	public static final double MAX_LNG = 180.0d;
    //initialize once per app operation
    public static final Random RANDOM = new Random();
	/**
	 * Generate a random double number within a given range
	 * @param low
	 * @param high
	 * @return
	 */
	public static Double generateRandomDoubleInRange(double low, double high) {
		double percentage = Math.random();
		double result = low + (high - low) * percentage;
		return result;
	}

	/**
	 * Generate a random latitude-longitude pair within the given min and max values
	 * @param lowLat
	 * @param highLat
	 * @param lowLng
	 * @param highLng
	 * @return LatLng object for google maps
	 */
	public static LatLng generateRandomLatLng(double lowLat, double highLat, double lowLng, double highLng) {
		return new LatLng(generateRandomDoubleInRange(lowLat, highLat), generateRandomDoubleInRange(lowLng, highLng));
	}

	/**
	 * Generate a random latitude-longitude pair
	 * @return LatLng object for google maps
	 */
	public static LatLng generateRandomLatLng() {
		return generateRandomLatLng(MIN_LAT, MAX_LAT, MIN_LNG, MAX_LNG);
	}


    /**
     * Get a random number in the range 0 to max inclusive
     * @param max
     * @return
     */
    public static int getRandomUpTo(int max) {
        return getRandomInRange(0, max);
    }

    /**
     * Get a random number in the range min to max inclusive
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInRange(int min, int max) {
        int randomNum = RANDOM.nextInt((max-min) + 1) + min;
        return randomNum;
    }

    /**
     * Get a random number in the range [0, n)
     * @param n
     * @return
     */
    public static int nextInt(int n) {
        return RANDOM.nextInt(n);
    }

}
