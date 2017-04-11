package com.gloomy.service.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserProfileResponse implements Serializable {

    private static final long serialVersionUID = 290144058402724557L;

    private boolean status;
    private User user;
    private String message;
    private Set<Topic> topics;
}
