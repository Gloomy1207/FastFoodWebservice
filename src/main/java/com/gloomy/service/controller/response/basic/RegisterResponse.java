package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomy.beans.User;
import com.gloomy.service.ApiParameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class RegisterResponse implements Serializable {

    private static final long serialVersionUID = 2557261084599213418L;

    private boolean status;
    @JsonProperty(ApiParameter.ACCESS_TOKEN)
    private String accessToken;
    private String message;
    private User user;
}
