package com.gloomy.impl;

import com.gloomy.beans.FoodImage;
import com.gloomy.dao.FoodImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 21-Apr-17.
 */
@Service
public class FoodImageDAOImpl {

    private FoodImageDAO mFoodImageDAO;

    @Autowired
    public void setFoodImageDAO(FoodImageDAO mFoodImageDAO) {
        this.mFoodImageDAO = mFoodImageDAO;
    }

    public Page<FoodImage> getFoodImageByFoodId(int foodId, Pageable pageable) {
        return mFoodImageDAO.findByFood_FoodId(foodId, pageable);
    }
}
