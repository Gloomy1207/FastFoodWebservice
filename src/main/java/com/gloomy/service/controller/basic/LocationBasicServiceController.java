package com.gloomy.service.controller.basic;

import com.gloomy.beans.City;
import com.gloomy.impl.CityDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 04/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.LOCATION_ENDPOINT)
public class LocationBasicServiceController {

    private final CityDAOImpl mCityDAO;

    @Autowired
    public LocationBasicServiceController(CityDAOImpl mCityDAO) {
        this.mCityDAO = mCityDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<City> getDataForHome(Pageable pageable) {
        return mCityDAO.findAll(pageable);
    }
}
