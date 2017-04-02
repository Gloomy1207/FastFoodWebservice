package com.gloomy.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface UserRole {
    String ADMIN = "ROLE_ADMIN";
    String USER = "ROLE_USER";
    String GUEST = "ROLE_GUEST";
}
