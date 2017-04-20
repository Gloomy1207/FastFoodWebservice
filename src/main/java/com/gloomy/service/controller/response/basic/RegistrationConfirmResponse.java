package com.gloomy.service.controller.response.basic;

import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Builder
@Getter
public class RegistrationConfirmResponse {
    private boolean status;
    private String message;
}
