package com.gloomy.service.controller.basic;

import com.gloomy.beans.FoodImage;
import com.gloomy.beans.TopicImage;
import com.gloomy.impl.FoodImageDAOImpl;
import com.gloomy.impl.TopicImageDAOImpl;
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
    private final TopicImageDAOImpl mTopicImageDAO;

    @Autowired
    public ImageBasicRestController(FoodImageDAOImpl mFoodImageDAO, TopicImageDAOImpl mTopicImageDAO) {
        this.mFoodImageDAO = mFoodImageDAO;
        this.mTopicImageDAO = mTopicImageDAO;
    }

    @GetMapping(value = ApiMappingUrl.FOOD_ENDPOINT)
    public Page<FoodImage> getDataForHome(@RequestParam(ApiParameter.FOOD_ID) int foodId,
                                          Pageable pageable) {
        return mFoodImageDAO.getFoodImageByFoodId(foodId, pageable);
    }

    @GetMapping(ApiMappingUrl.TOPIC_ENDPOINT)
    public Page<TopicImage> getTopicImages(@RequestParam(ApiParameter.TOPIC_ID) int topicId,
                                           Pageable pageable) {
        return mTopicImageDAO.getImagesByTopicId(topicId, pageable);
    }
}
