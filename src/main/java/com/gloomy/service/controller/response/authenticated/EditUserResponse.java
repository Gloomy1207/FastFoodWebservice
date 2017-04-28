package com.gloomy.service.controller.response.authenticated;

import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 28-Apr-17.
 */
@Builder
@Getter
public class EditUserResponse {
    private boolean status;
    private String message;
}
