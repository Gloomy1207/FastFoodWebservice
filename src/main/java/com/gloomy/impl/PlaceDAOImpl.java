package com.gloomy.impl;

import com.gloomy.beans.LatLng;
import com.gloomy.beans.Place;
import com.gloomy.dao.PlaceDAO;
import com.gloomy.utils.GeoIPUtil;
import com.gloomy.utils.LocationUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Service
public class PlaceDAOImpl {
    private PlaceDAO mPlaceDAO;

    @Autowired
    public void setPlaceDAO(PlaceDAO mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    public Page<Place> findAllPageable(Pageable pageable) {
        return mPlaceDAO.findAll(pageable);
    }

    public List<Place> findAllNearPlace(LatLng latLng) {
        if (latLng == null) {
            return mPlaceDAO.findAll();
        }
        final int distanceAverage = 20 * 1000;
        List<Place> places = new ArrayList<>();
        for (Place place : mPlaceDAO.findAll()) {
            if (place.getPlaceAddress() != null) {
                LatLng placeLatLng = LatLng.builder()
                        .lat(place.getPlaceAddress().getLat())
                        .lng(place.getPlaceAddress().getLng()).build();
                if (LocationUtil.getDistanceBetweenTwoPoint(latLng, placeLatLng) <= distanceAverage) {
                    places.add(place);
                }
            }
        }
        return places;
    }

    public Page<Place> findNearPlacePageable(Double lat, Double lng, HttpServletRequest request, Pageable pageable) {
        LatLng latLng = GeoIPUtil.createLatLngFromIp(lat, lng, request);
        List<Place> nearPlace = findAllNearPlace(latLng);
        return new PageImpl<>(nearPlace, pageable, nearPlace.size());
    }

    public Set<Place> searchPlace(String keyword) {
        if (TextUtils.isTime(keyword)) {
            return mPlaceDAO.searchWithTime(keyword, TextUtils.getTime(keyword));
        } else {
            return mPlaceDAO.searchWithoutTime(keyword);
        }
    }

    public Page<Place> findPlaceByRatingPageable(Pageable pageable) {
        return mPlaceDAO.findPlaceRating(pageable);
    }
}
