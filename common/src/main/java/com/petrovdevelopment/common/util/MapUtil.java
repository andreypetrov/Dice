package com.petrovdevelopment.common.util;

import com.google.android.gms.maps.model.LatLng;

public class MapUtil {

	/**
	 * Get the distance in meters between two points on the map
	 * 
	 * @param a first point
	 * @param b second point
	 * @return
	 */
	public static double distance(LatLng a, LatLng b) {
		return distance(a.latitude, a.longitude, b.latitude, b.longitude);
	}

    /**
     * Get the distance in meters between two points on the map
     * @param lat_a first point's latitude
     * @param lng_a first point's longitude
     * @param lat_b second point's latitude
     * @param lng_b second point's longitude
     * @return
     */
	public static double distance(double lat_a, double lng_a, double lat_b, double lng_b) {
		double earthRadius = 3958.75;
		double latDiff = Math.toRadians(lat_b - lat_a);
		double lngDiff = Math.toRadians(lng_b - lng_a);
		double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
				* Math.sin(lngDiff / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = earthRadius * c;
		int meterConversion = 1609;
		return Double.valueOf(distance * meterConversion);
	}
}
