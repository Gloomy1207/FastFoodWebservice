package com.gloomy.impl;

import com.gloomy.beans.Food;
import com.gloomy.beans.FoodPrice;
import com.gloomy.dao.FoodPriceDAO;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Service
public class FoodPriceDAOImpl {

    private FoodPriceDAO mFoodPriceDAO;

    @Autowired
    public void setFoodPriceDAO(FoodPriceDAO mFoodPriceDAO) {
        this.mFoodPriceDAO = mFoodPriceDAO;
    }

    public List<Food> searchFood(String keyword) {
        List<Food> foods = new ArrayList<>();
        if (TextUtils.isNumber(keyword)) {
            List<FoodPrice> foodPrices = mFoodPriceDAO.search(Double.parseDouble(keyword));
            addFoodFromFoodPrice(foods, foodPrices);
        }
        return foods;
    }

    private void addFoodFromFoodPrice(List<Food> foods, List<FoodPrice> foodPrices) {
        for (FoodPrice foodPrice : foodPrices) {
            if (!foods.contains(foodPrice.getFood())) {
                foods.add(foodPrice.getFood());
            }
        }
    }
}
