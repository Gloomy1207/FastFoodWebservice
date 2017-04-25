package com.gloomy.service.controller.basic;

import com.gloomy.beans.Place;
import com.gloomy.beans.PlaceComment;
import com.gloomy.impl.PlaceCommentDAOImpl;
import com.gloomy.impl.PlaceDAOImpl;
import com.gloomy.impl.PlaceFoodDAOImpl;
import com.gloomy.impl.PlaceRatingDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.PLACE_ENDPOINT)
public class PlaceBasicRestController {

    private final PlaceDAOImpl mPlaceDAOImp;
    private final PlaceRatingDAOImpl mPlaceRatingDAO;
    private final PlaceCommentDAOImpl mPlaceCommentDAO;
    private final PlaceFoodDAOImpl mPlaceFoodDAO;

    @Autowired
    public PlaceBasicRestController(PlaceDAOImpl mPlaceDAOImp, PlaceRatingDAOImpl mPlaceRatingDAO, PlaceCommentDAOImpl mPlaceCommentDAO, PlaceFoodDAOImpl mPlaceFoodDAO) {
        this.mPlaceDAOImp = mPlaceDAOImp;
        this.mPlaceRatingDAO = mPlaceRatingDAO;
        this.mPlaceCommentDAO = mPlaceCommentDAO;
        this.mPlaceFoodDAO = mPlaceFoodDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getDataForHome(Pageable pageable, HttpServletRequest request) {
        return mPlaceDAOImp.findAllPageable(pageable, request);
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<Place> getNearPlace(@RequestParam(required = false, name = "lat") Double lat,
                                    @RequestParam(required = false, name = "lng") Double lng,
                                    HttpServletRequest request, Pageable pageable) {
        return mPlaceDAOImp.findNearPlacePageable(lat, lng, request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.RATING)
    public Page<Place> getPlaceByRating(Pageable pageable) {
        return mPlaceRatingDAO.getPlaceOrderByPlaceRating(pageable);
    }

    @GetMapping(value = ApiMappingUrl.COMMENT)
    public Page<PlaceComment> getPlaceComment(@RequestParam(name = ApiParameter.PLACE_ID) int placeId,
                                              Pageable pageable,
                                              HttpServletRequest request) {
        return mPlaceCommentDAO.getCommentByPlaceId(placeId, pageable, request);
    }

    @GetMapping(value = ApiMappingUrl.FOOD_ENDPOINT)
    public ResponseEntity<?> getPlaceFood(@RequestParam(name = ApiParameter.PLACE_ID) int placeId,
                                          Pageable pageable,
                                          HttpServletRequest request) {
        return mPlaceFoodDAO.getPlaceFoodByPlaceId(placeId, pageable, request);
    }
}
