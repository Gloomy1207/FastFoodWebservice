package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.PlaceRating;
import com.gloomy.beans.User;
import com.gloomy.dao.PlaceRatingDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.controller.request.PlaceRatingRequest;
import com.gloomy.service.controller.response.authenticated.PlaceRatingResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import com.gloomy.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Apr-17.
 */
@Service
public class PlaceRatingDAOImpl {

    private PlaceRatingDAO mPlaceRatingDAO;
    private UserDAOImpl mUserDAO;
    private JwtTokenUtil mTokenUtil;
    private PlaceDAOImpl mPlaceDAO;
    private RatingTypeDAOImpl mRatingTypeDAO;
    private MessageSource mMessageSource;

    @Autowired
    public void setPlaceRatingDAO(PlaceRatingDAO mPlaceRatingDAO) {
        this.mPlaceRatingDAO = mPlaceRatingDAO;
    }

    @Autowired
    public void setUserDAO(UserDAOImpl mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Autowired
    public void setTokenUtil(JwtTokenUtil mTokenUtil) {
        this.mTokenUtil = mTokenUtil;
    }

    @Autowired
    public void setPlaceDAO(PlaceDAOImpl mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    @Autowired
    public void setRatingTypeDAO(RatingTypeDAOImpl mRatingTypeDAO) {
        this.mRatingTypeDAO = mRatingTypeDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public Page<Place> getPlaceOrderByPlaceRating(Pageable pageable) {
        List<PlaceRating> placeRatings = mPlaceRatingDAO.findPlaceByOrderByRating(pageable);
        List<Place> places = new ArrayList<>();
        for (PlaceRating placeRating : placeRatings) {
            places.add(placeRating.getPlace());
        }
        return new PageImpl<>(places, pageable, places.size());
    }

    public ResponseEntity<?> ratingPlace(PlaceRatingRequest ratingRequest, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        int placeId = ratingRequest.getPlaceId();
        List<PlaceRatingRequest.PlaceRatingItem> placeRatingItems = ratingRequest.getPlaceRatingItems();
        boolean status;
        String message;
        if (TextUtils.isEmpty(token)) {
            status = false;
            message = mMessageSource.getMessage("message.requestLogin", null, request.getLocale());
        } else {
            User user = UserUtil.getUserFromRequest(request, mUserDAO, mTokenUtil);
            Place place = mPlaceDAO.getPlaceById(placeId);
            for (PlaceRatingRequest.PlaceRatingItem item : placeRatingItems) {
                mPlaceRatingDAO.save(PlaceRating.builder()
                        .place(place)
                        .ratingType(mRatingTypeDAO.getRatingTypeById(item.getRatingTypeId()))
                        .user(user)
                        .star(item.getValue())
                        .build());
            }
            status = true;
            message = mMessageSource.getMessage("message.ratingPlaceComplete", null, request.getLocale());
        }
        return ResponseEntity.ok(PlaceRatingResponse.builder()
                .status(status)
                .message(message)
                .build());
    }
}
