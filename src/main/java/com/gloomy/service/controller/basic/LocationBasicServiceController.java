package com.gloomy.service.controller.basic;

import com.gloomy.beans.City;
import com.gloomy.beans.Province;
import com.gloomy.dao.CityDAO;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 04/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.LOCATION_ENDPOINT)
public class LocationBasicServiceController {

    private final CityDAO mCityDAO;

    @Autowired
    public LocationBasicServiceController(CityDAO mCityDAO) {
        this.mCityDAO = mCityDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<City> getDataForHome(Pageable pageable) {
        Page<City> cities = mCityDAO.findAll(pageable);
        for (City city : cities) {
            city.setProvinces(getFirstFiveItem(city));
        }
        return cities;
    }

    private Set<Province> getFirstFiveItem(City city) {
        int i = 0;
        Set<Province> result = new HashSet<>();
        for (Province province : city.getProvinces()) {
            result.add(province);
            i++;
            if (i == 5) {
                break;
            }
        }
        return result;
    }
}
