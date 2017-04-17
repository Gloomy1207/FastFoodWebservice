package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.controller.response.ResponseMessageConstant;
import com.gloomy.service.controller.response.UserProfileResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import com.gloomy.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */
@Service
public class UserDAOImpl {
    private UserDAO mUserDAO;
    private JwtTokenUtil mTokenUtil;

    @Autowired
    public void setUserDAO(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Autowired
    public void setTokenUtil(JwtTokenUtil tokenUtil) {
        mTokenUtil = tokenUtil;
    }

    public Page<Place> getUserFavoritePlace(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = mUserDAO.findUserByUsername(mTokenUtil.getUsernameFromToken(token));
        List<Place> places = new ArrayList<>();
        places.addAll(user.getUserFavoritePlaces());
        return new PageImpl<>(places, pageable, places.size());
    }

    public Page<User> findAllPaginateOrderByPoint(HttpServletRequest request, Pageable pageable) {
        Page<User> users = mUserDAO.findUserOrderByPoint(pageable);
        for (User user : users) {
            user.setAvatar(UserUtil.getUserAvatarPath(user, request));
        }
        return users;
    }

    public Set<User> searchUser(String keyword) {
        return mUserDAO.search(keyword);
    }

    public Page<User> getRatingUser(Pageable pageable, HttpServletRequest request) {
        Page<User> users = mUserDAO.findRatingUser(pageable);
        for (User user : users) {
            user.setAvatar(UserUtil.getUserAvatarPath(user, request));
        }
        return users;
    }

    public User getUserByUsername(String username) {
        return mUserDAO.findUserByUsername(username);
    }

    public User save(User user) {
        return mUserDAO.save(user);
    }

    public UserProfileResponse getUserProfile(String username, HttpServletRequest request) {
        User user;
        if (!TextUtils.isEmpty(username)) {
            user = getUserByUsername(username);
        } else {
            String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
            user = getUserByUsername(mTokenUtil.getUsernameFromToken(token));
        }
        if (user == null) {
            return UserProfileResponse.builder()
                    .message(ResponseMessageConstant.USER_NOT_EXIST_MESSAGE_EN)
                    .status(false)
                    .build();
        } else {
            user.setAvatar(UserUtil.getUserAvatarPath(user, request));
            List<Topic> topics = new ArrayList<>(user.getTopics());
            for (Topic topic : topics) {
                topic.setUser(null);
            }
            Page<Topic> topicPage = new PageImpl<>(topics);
            List<Place> places = new ArrayList<>(user.getUserFavoritePlaces());
            Page<Place> placePage = new PageImpl<>(places);
            return UserProfileResponse.builder()
                    .status(true)
                    .user(user)
                    .topics(topicPage)
                    .favoritePlaces(placePage)
                    .build();
        }
    }

    public Page<Topic> getUserFeeds(HttpServletRequest request, Pageable pageable) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = mUserDAO.findUserByUsername(mTokenUtil.getUsernameFromToken(token));
        List<Topic> topics = new ArrayList<>(user.getTopics());
        return new PageImpl<>(topics, pageable, topics.size());
    }
}
