package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomy.beans.PlaceFood;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Getter
@Builder
public class PlaceFoodResponse {
    private boolean status;
    private String message;
    @JsonProperty("store_foods_pageable")
    private Page<PlaceFood> placeFoods;
}
