package com.gloomy.impl;

import com.gloomy.beans.PlaceImage;
import com.gloomy.dao.PlaceImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 27-Apr-17.
 */
@Service
public class PlaceImageDAOImpl {
    private PlaceImageDAO mPlaceImageDAO;

    @Autowired
    public void setPlaceImageDAO(PlaceImageDAO mPlaceImageDAO) {
        this.mPlaceImageDAO = mPlaceImageDAO;
    }

    public Page<PlaceImage> getPlaceImageByPlaceId(int placeId, Pageable pageable) {
        return mPlaceImageDAO.findByPlacePlaceId(placeId, pageable);
    }
}
