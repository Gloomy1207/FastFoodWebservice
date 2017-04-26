package com.gloomy.service.controller.basic;

import com.gloomy.beans.City;
import com.gloomy.beans.Food;
import com.gloomy.beans.Place;
import com.gloomy.beans.Province;
import com.gloomy.impl.CityDAOImpl;
import com.gloomy.impl.PlaceDAOImpl;
import com.gloomy.impl.PlaceFoodDAOImpl;
import com.gloomy.impl.ProvinceDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 04/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.LOCATION_ENDPOINT)
public class LocationBasicRestController {

    private final CityDAOImpl mCityDAO;
    private final PlaceFoodDAOImpl mPlaceFoodDAO;
    private final PlaceDAOImpl mPlaceDAO;
    private final ProvinceDAOImpl mProvinceDAO;

    @Autowired
    public LocationBasicRestController(CityDAOImpl mCityDAO, PlaceFoodDAOImpl mPlaceFoodDAO, PlaceDAOImpl mPlaceDAO, ProvinceDAOImpl mProvinceDAO) {
        this.mCityDAO = mCityDAO;
        this.mPlaceFoodDAO = mPlaceFoodDAO;
        this.mPlaceDAO = mPlaceDAO;
        this.mProvinceDAO = mProvinceDAO;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<City> getDataForHome(Pageable pageable) {
        return mCityDAO.findAllForHome(pageable);
    }

    @GetMapping(value = ApiMappingUrl.FOOD_ENDPOINT)
    public Page<Food> getLocationFood(@RequestParam(ApiParameter.LOCATION_ID) int locationId,
                                      @RequestParam(ApiParameter.LOCATION_TYPE) int locationType,
                                      Pageable pageable) {
        return mPlaceFoodDAO.getLocationFood(locationId, locationType, pageable);
    }

    @GetMapping(value = ApiMappingUrl.PLACE_ENDPOINT)
    public Page<Place> getLocationPlace(@RequestParam(ApiParameter.LOCATION_ID) int locationId,
                                        @RequestParam(ApiParameter.LOCATION_TYPE) int locationType,
                                        Pageable pageable,
                                        HttpServletRequest request) {
        return mPlaceDAO.getLocationPlace(locationId, locationType, pageable, request);
    }

    @GetMapping(value = ApiMappingUrl.PROVINCE)
    public Set<Province> getCityProvince(@RequestParam(ApiParameter.CITY_ID) int cityId) {
        return mProvinceDAO.getProvinceByCityId(cityId);
    }
}
