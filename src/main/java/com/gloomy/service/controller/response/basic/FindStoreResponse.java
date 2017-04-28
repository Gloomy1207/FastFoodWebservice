package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gloomy.beans.Place;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 27-Apr-17.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindStoreResponse {
    private boolean status;
    private String message;
    private Place place;
}
