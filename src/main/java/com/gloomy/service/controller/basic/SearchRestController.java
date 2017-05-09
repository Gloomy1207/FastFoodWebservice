package com.gloomy.service.controller.basic;

import com.gloomy.beans.*;
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

import javax.servlet.http.HttpServletRequest;
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

    private final PlaceTypeDAOImpl mPlaceTypeDAO;

    private final PlaceFoodDAOImpl mPlaceFoodDAO;

    @Autowired
    public SearchRestController(FoodDAOImpl mFoodDAO, UserDAOImpl mUserDAO, PlaceDAOImpl mPlaceDAO, TopicDAOImpl mTopicDAO, PlaceTypeDAOImpl mPlaceTypeDAO, PlaceFoodDAOImpl mPlaceFoodDAO) {
        this.mFoodDAO = mFoodDAO;
        this.mUserDAO = mUserDAO;
        this.mPlaceDAO = mPlaceDAO;
        this.mTopicDAO = mTopicDAO;
        this.mPlaceTypeDAO = mPlaceTypeDAO;
        this.mPlaceFoodDAO = mPlaceFoodDAO;
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public ResponseEntity<?> searchKeyword(@RequestParam(name = ApiParameter.KEYWORD) String keyword,
                                           HttpServletRequest request) {
        Set<Food> foods = mFoodDAO.searchFood(keyword);
        foods.addAll(mPlaceFoodDAO.searchFood(keyword));
        Set<User> peoples = mUserDAO.searchUser(keyword);
        Set<Place> stores = mPlaceDAO.searchPlace(keyword, request);
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
