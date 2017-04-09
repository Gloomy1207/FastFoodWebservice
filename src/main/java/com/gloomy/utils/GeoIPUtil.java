package com.gloomy.utils;

import com.gloomy.beans.LatLng;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
public final class GeoIPUtil {

    private GeoIPUtil() {
        // No-op
    }

    public static CityResponse getIpCity(HttpServletRequest request) {
        try {
            File file = ResourceUtils.getFile("classpath:data/GeoLite2-City.mmdb");
            DatabaseReader databaseReader = new DatabaseReader.Builder(file).build();
            return databaseReader.city(InetAddress.getByName(request.getRemoteHost()));
        } catch (java.io.IOException | GeoIp2Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static LatLng createLatLngFromIp(Double defaultLat, Double defaultLng, HttpServletRequest request) {
        LatLng latLng = null;
        if (defaultLat != null && defaultLng != null) {
            latLng = LatLng.builder().lng(defaultLng).lat(defaultLat).build();
        } else {
            CityResponse cityResponse = GeoIPUtil.getIpCity(request);
            if (cityResponse != null) {
                latLng = LatLng.builder()
                        .lat(cityResponse.getLocation().getLatitude())
                        .lng(cityResponse.getLocation().getLongitude()).build();
            }
        }
        return latLng;
    }
}
