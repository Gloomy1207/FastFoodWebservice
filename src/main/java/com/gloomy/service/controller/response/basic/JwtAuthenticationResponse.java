package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomy.beans.User;
import com.gloomy.service.ApiParameter;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 6126659231711840730L;

    @JsonProperty(value = ApiParameter.ACCESS_TOKEN)
    private String accessToken;
    private boolean status;
    private String message;
    private User user;
}
