package com.gloomy.service.controller.basic;

import com.gloomy.beans.Food;
import com.gloomy.beans.FoodRating;
import com.gloomy.dao.FoodDAO;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.FOOD_ENDPOINT)
public class FoodBasicServiceController {

    private final FoodDAO mFoodDAO;

    @Autowired
    public FoodBasicServiceController(FoodDAO mFoodDAO) {
        this.mFoodDAO = mFoodDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Food> getDataForHome(Pageable pageable) {
        Page<Food> foods = mFoodDAO.findAll(pageable);
        for (Food food : foods) {
            food.setRating(getFoodRating(food));
            food.setNumberOfRating(food.getFoodRatings().size());
        }
        return foods;
    }

    private float getFoodRating(Food food) {
        int total = 0;
        if (food != null && food.getFoodRatings() != null && food.getFoodRatings().size() > 0) {
            for (FoodRating rating : food.getFoodRatings()) {
                total += rating.getStar();
            }
            return total / food.getFoodRatings().size();
        }
        return total;
    }
}
