package com.gloomy.service.response;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface FailureType {
    int USER_EXIST = 1;
    int USERNAME_EMPTY = 2;
    int PASSWORD_EMPTY = 3;
    int SERVER_ERROR = 99;
}
