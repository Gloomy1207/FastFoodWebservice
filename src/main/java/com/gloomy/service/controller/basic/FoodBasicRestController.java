package com.gloomy.service.controller.basic;

import com.gloomy.beans.Food;
import com.gloomy.impl.FoodDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.FOOD_ENDPOINT)
public class FoodBasicRestController {
    private final FoodDAOImpl mFoodDAOImpl;

    @Autowired
    public FoodBasicRestController(FoodDAOImpl mFoodDAOImpl) {
        this.mFoodDAOImpl = mFoodDAOImpl;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Food> getDataForHome(Pageable pageable) {
        return mFoodDAOImpl.findAllPaginate(pageable);
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<Food> getDataForSearch(@RequestParam(name = "lat", required = false) Double lat,
                                       @RequestParam(name = "lng", required = false) Double lng,
                                       HttpServletRequest request, Pageable pageable) {
        return mFoodDAOImpl.findNearFood(lat, lng, request, pageable);
    }
}
