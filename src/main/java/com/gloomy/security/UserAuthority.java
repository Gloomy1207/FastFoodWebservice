package com.gloomy.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface UserAuthority {
    String POST = "post";
    String EDIT = "edit";
    String EDIT_OWN = "edit_own";
    String VIEW = "view";
    String DELETE = "delete";
    String DELETE_OWN = "delete_own";
}
