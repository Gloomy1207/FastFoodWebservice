package com.gloomy.service.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gloomy.beans.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse implements Serializable {

    private static final long serialVersionUID = 290144058402724557L;

    private boolean status;
    private User user;
    private String message;
}
