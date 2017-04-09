package com.gloomy.utils;

import com.gloomy.beans.LatLng;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
public final class LocationUtil {

    private LocationUtil() {
        // No-op
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <a>
     * http://stackoverflow.com/a/16794680
     * </a>
     *
     * @param first  fistLatLng
     * @param second secondLatLng
     * @return distance in metter
     */
    public static double getDistanceBetweenTwoPoint(LatLng first, LatLng second) {
        final int R = 6371; // Radius of the earth
        Double latDistance = Math.toRadians(second.getLat() - first.getLat());
        Double lonDistance = Math.toRadians(second.getLng() - first.getLng());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(first.getLat())) * Math.cos(Math.toRadians(second.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
