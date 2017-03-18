package com.gloomy.service;

import com.gloomy.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
@RequestMapping(value = ApiMappingUrl.API_BASE_URL)
public abstract class RestServiceController {
    protected final UserDAO mUserDAO;

    @Autowired
    public RestServiceController(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }
}
