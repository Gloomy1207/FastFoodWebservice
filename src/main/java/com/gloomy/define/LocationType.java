package com.gloomy.define;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26-Apr-17.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface LocationType {
    int CITY = 1;
    int PROVINCE = 2;
}
