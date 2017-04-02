package com.gloomy.service.controller.basic;

import com.gloomy.dao.UserDAO;
import com.gloomy.service.RestServiceController;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
public class UserBasicServiceController extends RestServiceController {
    public UserBasicServiceController(UserDAO mUserDAO) {
        super(mUserDAO);
    }
}
