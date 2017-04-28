package com.gloomy.impl;

import com.gloomy.beans.Food;
import com.gloomy.beans.PlaceFood;
import com.gloomy.dao.PlaceFoodDAO;
import com.gloomy.define.LocationType;
import com.gloomy.service.controller.response.basic.PlaceFoodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Apr-17.
 */
@Service
public class PlaceFoodDAOImpl {

    private PlaceFoodDAO mPlaceFoodDAO;
    private MessageSource mMessageSource;

    @Autowired
    public void setPlaceFoodDAO(PlaceFoodDAO mPlaceFoodDAO) {
        this.mPlaceFoodDAO = mPlaceFoodDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public ResponseEntity<?> getPlaceFoodByPlaceId(int placeId, Pageable pageable, HttpServletRequest request) {
        Page<PlaceFood> placeFoods = mPlaceFoodDAO.findAllByPlacePlaceId(placeId, pageable);
        boolean status = true;
        String message = null;
        if (placeFoods.getContent().isEmpty()) {
            status = false;
            message = mMessageSource.getMessage("message.placeFoodEmpty", null, request.getLocale());
        }
        return ResponseEntity.ok(PlaceFoodResponse.builder()
                .message(message)
                .placeFoods(placeFoods)
                .status(status)
                .build());
    }

    public Page<Food> getLocationFood(int locationId, int locationType, Pageable pageable) {
        Page<PlaceFood> placeFoods;
        if (locationType == LocationType.CITY) {
            placeFoods = mPlaceFoodDAO.findAllByPlace_PlaceAddress_Province_CityCityId(locationId, pageable);
        } else if (locationType == LocationType.PROVINCE) {
            placeFoods = mPlaceFoodDAO.findAllByPlace_PlaceAddress_ProvinceProvinceId(locationId, pageable);
        } else {
            return null;
        }
        List<Food> foods = new ArrayList<>();
        for (PlaceFood placeFood : placeFoods) {
            foods.add(placeFood.getFood());
        }
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > foods.size() ? foods.size() : (start + pageable.getPageSize());
        return new PageImpl<>(foods.subList(start, end), pageable, foods.size());
    }
}
