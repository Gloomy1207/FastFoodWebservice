package com.gloomy.service.controller.basic;

import com.gloomy.beans.Place;
import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserBasicServiceController {

    private final UserDAO mUserDAO;
    private final JwtTokenUtil mJwtTokenUtil;

    @Autowired
    public UserBasicServiceController(UserDAO mUserDAO, JwtTokenUtil mJwtTokenUtil) {
        this.mUserDAO = mUserDAO;
        this.mJwtTokenUtil = mJwtTokenUtil;
    }

    @GetMapping(value = ApiMappingUrl.HOME)
    public Page<Place> getUserFavoritePlace(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = mUserDAO.findUserByUsername(mJwtTokenUtil.getUsernameFromToken(token));
        List<Place> places = new ArrayList<>();
        places.addAll(user.getPlaces());
        return new PageImpl<>(places, pageable, places.size());
    }
}
