package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.PlaceRating;
import com.gloomy.dao.PlaceRatingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Apr-17.
 */
@Service
public class PlaceRatingDAOImpl {

    private PlaceRatingDAO mPlaceRatingDAO;

    @Autowired
    public void setPlaceRatingDAO(PlaceRatingDAO mPlaceRatingDAO) {
        this.mPlaceRatingDAO = mPlaceRatingDAO;
    }

    public Page<Place> getPlaceOrderByPlaceRating(Pageable pageable) {
        List<PlaceRating> placeRatings = mPlaceRatingDAO.findPlaceByOrderByRating(pageable);
        List<Place> places = new ArrayList<>();
        for (PlaceRating placeRating : placeRatings) {
            places.add(placeRating.getPlace());
        }
        return new PageImpl<>(places, pageable, places.size());
    }
}
