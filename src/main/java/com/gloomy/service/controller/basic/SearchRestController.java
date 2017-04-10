package com.gloomy.service.controller.basic;

import com.gloomy.beans.Food;
import com.gloomy.beans.Place;
import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.impl.*;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.response.basic.SearchResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL)
public class SearchRestController {

    private final FoodDAOImpl mFoodDAO;

    private final UserDAOImpl mUserDAO;

    private final PlaceDAOImpl mPlaceDAO;

    private final TopicDAOImpl mTopicDAO;

    private final FoodPriceDAOImpl mFoodPriceDAO;

    private final PlaceTypeDAOImpl mPlaceTypeDAO;

    @Autowired
    public SearchRestController(FoodDAOImpl mFoodDAO, UserDAOImpl mUserDAO, PlaceDAOImpl mPlaceDAO, TopicDAOImpl mTopicDAO, FoodPriceDAOImpl mFoodPriceDAO, PlaceTypeDAOImpl mPlaceTypeDAO) {
        this.mFoodDAO = mFoodDAO;
        this.mUserDAO = mUserDAO;
        this.mPlaceDAO = mPlaceDAO;
        this.mTopicDAO = mTopicDAO;
        this.mFoodPriceDAO = mFoodPriceDAO;
        this.mPlaceTypeDAO = mPlaceTypeDAO;
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public ResponseEntity<?> searchKeyword(@RequestParam(name = ApiParameter.KEYWORD) String keyword) {
        Set<Food> foods = mFoodDAO.searchFood(keyword);
        foods.addAll(mFoodPriceDAO.searchFood(keyword));
        Set<User> peoples = mUserDAO.searchUser(keyword);
        Set<Place> stores = mPlaceDAO.searchPlace(keyword);
        stores.addAll(mPlaceTypeDAO.searchPlace(keyword));
        Set<Topic> topics = mTopicDAO.searchTopic(keyword);
        return ResponseEntity.ok(SearchResultResponse.builder()
                .isSuccess(true)
                .foods(foods)
                .peoples(peoples)
                .stores(stores)
                .topics(topics)
                .build());
    }
}
