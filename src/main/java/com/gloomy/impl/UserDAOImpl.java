package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.utils.JwtTokenUtil;
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

    public Page<User> findAllPaginateOrderByPoint(Pageable pageable) {
        return mUserDAO.findUserOrderByPoint(pageable);
    }

    public Set<User> searchUser(String keyword) {
        return mUserDAO.search(keyword);
    }

    public Page<User> getRatingUser(Pageable pageable) {
        return mUserDAO.findRatingUser(pageable);
    }

    public User findUserByUsername(String username) {
        return mUserDAO.findUserByUsername(username);
    }
}
