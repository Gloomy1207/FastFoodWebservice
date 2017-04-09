package com.gloomy.impl;

import com.gloomy.beans.City;
import com.gloomy.dao.CityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Service
public class CityDAOImpl {
    private CityDAO mCityDAO;

    @Autowired
    public void setCityDAO(CityDAO mCityDAO) {
        this.mCityDAO = mCityDAO;
    }

    public Page<City> findAll(Pageable pageable) {
        Page<City> cities = mCityDAO.findAll(pageable);
        for (City city : cities) {
            city.setProvinces(city.getFirstFiveProvince());
        }
        return cities;
    }
}
