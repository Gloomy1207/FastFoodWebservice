package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.Role;
import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.controller.response.UserProfileResponse;
import com.gloomy.service.controller.response.authenticated.UserFavoriteResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import com.gloomy.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private RoleDAOImpl mRoleDAO;
    private MessageSource mMessageSource;
    private BCryptPasswordEncoder mBCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setUserDAO(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Autowired
    public void setTokenUtil(JwtTokenUtil tokenUtil) {
        mTokenUtil = tokenUtil;
    }

    @Autowired
    public void setRoleDAO(RoleDAOImpl roleDAO) {
        this.mRoleDAO = roleDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public UserFavoriteResponse getUserFavoritePlace(HttpServletRequest request, Pageable pageable) {
        UserFavoriteResponse response;
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = mUserDAO.findUserByUsername(mTokenUtil.getUsernameFromToken(token));
        if (user.isEnabled()) {
            List<Place> places = new ArrayList<>();
            places.addAll(user.getUserFavoritePlaces());
            if (!places.isEmpty()) {
                response = UserFavoriteResponse.builder()
                        .status(true)
                        .places(new PageImpl<>(places, pageable, places.size()))
                        .build();
            } else {
                String emptyFavorite = mMessageSource.getMessage("message.favoriteEmpty", null, request.getLocale());
                response = UserFavoriteResponse.builder()
                        .status(true)
                        .message(emptyFavorite)
                        .build();
            }
        } else {
            String requestActiveMessage = mMessageSource.getMessage("message.requestActive", null, request.getLocale());
            response = UserFavoriteResponse.builder()
                    .status(false)
                    .message(requestActiveMessage)
                    .build();
        }
        return response;
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

    public User getUserByEmail(String email) {
        return mUserDAO.findUserByEmail(email);
    }

    public void save(User user) {
        mUserDAO.save(user);
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
            String userNotExist = mMessageSource.getMessage("message.usernameNotExist", null, request.getLocale());
            return UserProfileResponse.builder()
                    .message(userNotExist)
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

    public User getUserByFacebook(String email, String facebookId) {
        return mUserDAO.findUserByFacebookIdAndAndEmail(email, facebookId);
    }

    public User registerUserByFacebook(String facebookToken, String facebookId, String name, String email, String avatar) {
        Role role = mRoleDAO.getRoleByRoleName(RoleDAOImpl.RoleName.USER);
        User user = User.builder()
                .facebookAccessToken(facebookToken)
                .facebookId(facebookId)
                .fullname(name)
                .email(email)
                .username(email)
                .role(role)
                .avatar(avatar)
                .build();
        return mUserDAO.save(user);
    }

    public User registerUser(String username, String password, String email) {
        Role role = mRoleDAO.getRoleByRoleName(RoleDAOImpl.RoleName.USER);
        User user = User.builder()
                .username(username)
                .password(mBCryptPasswordEncoder.encode(password))
                .email(email)
                .role(role)
                .build();
        return mUserDAO.save(user);
    }
}
