package com.gloomy.service.controller.basic;

import com.gloomy.beans.Place;
import com.gloomy.dao.PlaceDAO;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.PLACE_ENDPOINT)
public class PlaceBasicServiceController {

    private final PlaceDAO mPlaceDAO;

    @Autowired
    public PlaceBasicServiceController(PlaceDAO mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getDataForHome(Pageable pageable) {
        return mPlaceDAO.findAll(pageable);
    }
}
