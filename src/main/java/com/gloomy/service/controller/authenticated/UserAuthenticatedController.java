package com.gloomy.service.controller.authenticated;

import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.service.controller.response.ResponseMessageConstant;
import com.gloomy.service.controller.response.UserProfileResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.USER_ENDPOINT)
public class UserAuthenticatedController {

    private final UserDAOImpl mUserDAO;
    private final JwtTokenUtil mJwtTokenUtil;

    @Autowired
    public UserAuthenticatedController(UserDAOImpl userDAO, JwtTokenUtil mJwtTokenUtil) {
        this.mUserDAO = userDAO;
        this.mJwtTokenUtil = mJwtTokenUtil;
    }

    @GetMapping(value = ApiMappingUrl.MY_PROFILE_ENDPOINT)
    @ResponseBody
    public ResponseEntity<?> getProfile(@RequestParam(value = ApiParameter.USERNAME, required = false) String username,
                                        @RequestHeader(value = SecurityConstants.TOKEN_HEADER_NAME, required = false) String token) {
        User user;
        if (!TextUtils.isEmpty(username)) {
            user = mUserDAO.getUserByUsername(username);
        } else {
            user = mUserDAO.getUserByUsername(mJwtTokenUtil.getUsernameFromToken(token));
        }
        if (user == null) {
            return ResponseEntity.ok(UserProfileResponse.builder()
                    .message(ResponseMessageConstant.USER_NOT_EXIST_MESSAGE_EN)
                    .status(false)
                    .build());
        } else {
            Set<Topic> topics = user.getTopics();
            for (Topic topic : topics) {
                topic.setUser(null);
            }
            return ResponseEntity.ok(UserProfileResponse.builder()
                    .status(true)
                    .user(user)
                    .topics(topics)
                    .build());
        }
    }
}
