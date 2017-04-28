package com.gloomy.service.controller.authenticated;

import com.gloomy.beans.Topic;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserAuthenticatedController {

    private final UserDAOImpl mUserDAO;

    @Autowired
    public UserAuthenticatedController(UserDAOImpl userDAO) {
        this.mUserDAO = userDAO;
    }

    @GetMapping(value = ApiMappingUrl.USER_FAVORITE)
    public ResponseEntity<?> getUserFavorite(HttpServletRequest request, Pageable pageable) {
        return ResponseEntity.ok(mUserDAO.getUserFavoritePlace(request, pageable));
    }

    @GetMapping(value = ApiMappingUrl.USER_FEEDS)
    public Page<Topic> getUserTopic(HttpServletRequest request, Pageable pageable) {
        return mUserDAO.getUserFeeds(request, pageable);
    }
}
