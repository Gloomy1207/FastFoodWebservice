package com.gloomy.service.controller.response.authenticated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gloomy.beans.Place;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 20/04/2017.
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFavoriteResponse {
    private boolean status;
    private String message;
    private Page<Place> places;
}
