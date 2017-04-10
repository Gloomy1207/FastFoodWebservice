package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.PlaceType;
import com.gloomy.dao.PlaceTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Service
public class PlaceTypeDAOImpl {

    private PlaceTypeDAO mPlaceTypeDAO;

    @Autowired
    public void setPlaceTypeDAO(PlaceTypeDAO mPlaceTypeDAO) {
        this.mPlaceTypeDAO = mPlaceTypeDAO;
    }

    public Set<Place> searchPlace(String type) {
        Set<Place> places = new HashSet<>();
        Set<PlaceType> placeTypes = mPlaceTypeDAO.findPlaceTypeByTypeNameIsContaining(type);
        for (PlaceType placeType : placeTypes) {
            places.addAll(placeType.getPlaces());
        }
        return places;
    }
}
