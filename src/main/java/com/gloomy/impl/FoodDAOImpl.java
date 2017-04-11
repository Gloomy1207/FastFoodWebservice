package com.gloomy.impl;

import com.gloomy.beans.Food;
import com.gloomy.beans.LatLng;
import com.gloomy.dao.FoodDAO;
import com.gloomy.utils.GeoIPUtil;
import com.sun.istack.internal.Nullable;
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
public class FoodDAOImpl {
    private FoodDAO mFoodDAO;

    @Autowired
    public void setFoodDAO(FoodDAO mFoodDAO) {
        this.mFoodDAO = mFoodDAO;
    }

    public List<Food> getNearFood(@Nullable LatLng latLng) {
        if (latLng == null) {
            return mFoodDAO.findAll();
        }
        List<Food> foods = new ArrayList<>();
        for (Food food : mFoodDAO.findAll()) {
            food.getPlaceFoods().clear();
            food.getPlaceFoods().addAll(food.getNearPlace(latLng));
        }
        return foods;
    }

    public Page<Food> findAllPaginate(Pageable pageable) {
        return mFoodDAO.findAll(pageable);
    }

    public Page<Food> findNearFood(Double lat, Double lng, HttpServletRequest request, Pageable pageable) {
        Page<Food> foods;
        LatLng latLng = GeoIPUtil.createLatLngFromIp(lat, lng, request);
        List<Food> nearFoods = getNearFood(latLng);
        foods = new PageImpl<>(nearFoods, pageable, nearFoods.size());
        return foods;
    }

    public Set<Food> searchFood(String keyword) {
        return mFoodDAO.search(keyword);
    }
}
