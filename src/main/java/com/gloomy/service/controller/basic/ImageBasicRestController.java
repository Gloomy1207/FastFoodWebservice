package com.gloomy.service.controller.basic;

import com.gloomy.beans.FoodImage;
import com.gloomy.impl.FoodImageDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.IMAGE_ENDPOINT)
public class ImageBasicRestController {
    private final FoodImageDAOImpl mFoodImageDAO;

    @Autowired
    public ImageBasicRestController(FoodImageDAOImpl mFoodImageDAO) {
        this.mFoodImageDAO = mFoodImageDAO;
    }

    @GetMapping(value = ApiMappingUrl.FOOD_ENDPOINT)
    public Page<FoodImage> getDataForHome(@RequestParam(ApiParameter.FOOD_ID) int foodId,
                                          Pageable pageable) {
        return mFoodImageDAO.getFoodImageByFoodId(foodId, pageable);
    }
}
