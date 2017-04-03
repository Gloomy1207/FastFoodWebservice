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
public class PlaceBaseServiceController {

    private final PlaceDAO mPlaceDAO;

    @Autowired
    public PlaceBaseServiceController(PlaceDAO mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getDataForHome(Pageable pageable) {
        Page<Place> places = mPlaceDAO.findAll(pageable);
        for (Place place : places) {
            place.setPlaceTypeName(place.getPlaceType().getTypeName());
            place.setPlaceTypeId(place.getPlaceType().getPlaceTypeId());
        }
        return places;
    }
}
