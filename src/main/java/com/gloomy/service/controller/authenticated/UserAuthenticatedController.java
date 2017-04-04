package com.gloomy.service.controller.authenticated;

import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.response.UserProfileResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@RestController
@RequestMapping(name = (ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.USER_ENDPOINT))
public class UserAuthenticatedController {

    private final UserDAO mUserDAO;
    private final JwtTokenUtil mJwtTokenUtil;

    @Autowired
    public UserAuthenticatedController(UserDAO mUserDAO, JwtTokenUtil mJwtTokenUtil) {
        this.mUserDAO = mUserDAO;
        this.mJwtTokenUtil = mJwtTokenUtil;
    }

    @GetMapping(value = ApiMappingUrl.MY_PROFILE_ENDPOINT)
    @ResponseBody
    public ResponseEntity<?> getProfile(@RequestParam(value = ApiParameter.USERNAME, required = false) String username,
                                        @RequestHeader(value = SecurityConstants.TOKEN_HEADER_NAME, required = false) String token) {
        User user;
        if (!TextUtils.isEmpty(username)) {
            user = mUserDAO.findUserByUsername(username);
        } else {
            user = mUserDAO.findUserByUsername(mJwtTokenUtil.getUsernameFromToken(token));
        }
        if (user == null) {
            return ResponseEntity.ok(new UserProfileResponse(false, null, "User doesn't exist!"));
        } else {
            return ResponseEntity.ok(new UserProfileResponse(true, user, null));
        }
    }
}
