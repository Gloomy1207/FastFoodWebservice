package com.gloomy.service.controller.basic;

import com.gloomy.beans.Place;
import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserBasicRestController {

    private final UserDAOImpl mUserDAOImpl;

    @Autowired
    public UserBasicRestController(UserDAOImpl userDAOImpl) {
        this.mUserDAOImpl = userDAOImpl;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getUserFavoritePlace(HttpServletRequest request, Pageable pageable) {
        return mUserDAOImpl.getUserFavoritePlace(request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<User> getSearchUser(HttpServletRequest request, Pageable pageable) {
        return mUserDAOImpl.findAllPaginateOrderByPoint(request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.RATING)
    public Page<User> getRatingUser(Pageable pageable) {
        return mUserDAOImpl.getRatingUser(pageable);
    }
}
