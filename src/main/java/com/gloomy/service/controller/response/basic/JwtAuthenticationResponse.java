package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomy.service.ApiParameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class JwtAuthenticationResponse implements Serializable {

    @JsonProperty(value = ApiParameter.ACCESS_TOKEN)
    private String accessToken;

    private static final long serialVersionUID = 6126659231711840730L;
}
