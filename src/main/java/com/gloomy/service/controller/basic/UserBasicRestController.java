package com.gloomy.service.controller.basic;

import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserBasicRestController {

    private final UserDAOImpl mUserDAO;

    @Autowired
    public UserBasicRestController(UserDAOImpl userDAOImpl) {
        this.mUserDAO = userDAOImpl;
    }

    @GetMapping(value = ApiMappingUrl.SEARCH)
    public Page<User> getSearchUser(HttpServletRequest request, Pageable pageable) {
        return mUserDAO.findAllPaginateOrderByPoint(request, pageable);
    }

    @GetMapping(value = ApiMappingUrl.RATING)
    public Page<User> getRatingUser(Pageable pageable, HttpServletRequest request) {
        return mUserDAO.getRatingUser(pageable, request);
    }

    @GetMapping(value = ApiMappingUrl.MY_PROFILE_ENDPOINT)
    @ResponseBody
    public ResponseEntity<?> getProfile(@RequestParam(value = ApiParameter.USERNAME, required = false) String username,
                                        HttpServletRequest request) {
        return ResponseEntity.ok(mUserDAO.getUserProfile(username, request));
    }
}
