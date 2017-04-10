package com.gloomy.service.controller.basic;

import com.gloomy.beans.Place;
import com.gloomy.impl.PlaceDAOImpl;
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
 * Created by Gloomy on 03-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.PLACE_ENDPOINT)
public class PlaceBasicRestController {

    private final PlaceDAOImpl mPlaceDAOImp;

    @Autowired
    public PlaceBasicRestController(PlaceDAOImpl mPlaceDAOImp) {
        this.mPlaceDAOImp = mPlaceDAOImp;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getDataForHome(Pageable pageable) {
        return mPlaceDAOImp.findAll(pageable);
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<Place> getNearPlace(@RequestParam(required = false, name = "lat") Double lat,
                                    @RequestParam(required = false, name = "lng") Double lng,
                                    HttpServletRequest request, Pageable pageable) {
        return mPlaceDAOImp.findNearPlacePageable(lat, lng, request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.RATING)
    public Page<Place> getPlaceByRating(Pageable pageable) {
        return mPlaceDAOImp.findPlaceByRatingPageable(pageable);
    }
}
