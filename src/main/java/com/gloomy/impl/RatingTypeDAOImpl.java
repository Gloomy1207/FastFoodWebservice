package com.gloomy.impl;

import com.gloomy.beans.RatingType;
import com.gloomy.dao.RatingTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26/04/2017.
 */
@Service
public class RatingTypeDAOImpl {

    private RatingTypeDAO mRatingTypeDAO;

    @Autowired
    public void setRatingTypeDAO(RatingTypeDAO mRatingTypeDAO) {
        this.mRatingTypeDAO = mRatingTypeDAO;
    }

    public RatingType getRatingTypeById(int ratingTypeId) {
        return mRatingTypeDAO.findByRatingTypeId(ratingTypeId);
    }
}
