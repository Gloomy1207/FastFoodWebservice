package com.gloomy.service.controller.response.authenticated;

import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26/04/2017.
 */
@Builder
@Getter
public class PlaceRatingResponse {
    private boolean status;
    private String message;
}
