package com.gloomy.impl;

import com.gloomy.beans.LatLng;
import com.gloomy.beans.Place;
import com.gloomy.beans.User;
import com.gloomy.dao.PlaceDAO;
import com.gloomy.define.LocationType;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.controller.response.authenticated.LikeTopicResponse;
import com.gloomy.service.controller.response.basic.FindStoreResponse;
import com.gloomy.utils.*;
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
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Service
public class PlaceDAOImpl {
    private PlaceDAO mPlaceDAO;
    private UserDAOImpl mUserDAO;
    private JwtTokenUtil mTokenUtil;
    private UserFavoriteDAOImpl mUserFavoriteDAO;
    private MessageSource mMessageSource;

    @Autowired
    public void setPlaceDAO(PlaceDAO mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
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
    public void setUserFavoriteDAO(UserFavoriteDAOImpl mUserFavoriteDAO) {
        this.mUserFavoriteDAO = mUserFavoriteDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public Page<Place> findAllPageable(Pageable pageable, HttpServletRequest request) {
        Page<Place> places = mPlaceDAO.findAll(pageable);
        User user = UserUtil.getUserFromRequest(request, mUserDAO, mTokenUtil);
        if (user != null) {
            for (Place place : places) {
                place.setFavorite(mUserFavoriteDAO.isFavoritePlace(user, place));
            }
        }
        return places;
    }

    public List<Place> findAllNearPlace(LatLng latLng, HttpServletRequest request) {
        if (latLng == null) {
            return mPlaceDAO.findAll();
        }
        final int distanceAverage = 20 * 1000;
        List<Place> places = new ArrayList<>();
        User user = UserUtil.getUserFromRequest(request, mUserDAO, mTokenUtil);
        for (Place place : mPlaceDAO.findAll()) {
            if (place.getPlaceAddress() != null) {
                LatLng placeLatLng = LatLng.builder()
                        .lat(place.getPlaceAddress().getLat())
                        .lng(place.getPlaceAddress().getLng()).build();
                if (LocationUtil.getDistanceBetweenTwoPoint(latLng, placeLatLng) <= distanceAverage) {
                    places.add(place);
                }
            }
            if (user != null) {
                place.setFavorite(mUserFavoriteDAO.isFavoritePlace(user, place));
            }
        }
        return places;
    }

    public Page<Place> findNearPlacePageable(Double lat, Double lng, HttpServletRequest request, Pageable pageable) {
        LatLng latLng = GeoIPUtil.createLatLngFromIp(lat, lng, request);
        List<Place> nearPlace = findAllNearPlace(latLng, request);
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > nearPlace.size() ? nearPlace.size() : (start + pageable.getPageSize());
        return new PageImpl<>(nearPlace.subList(start, end), pageable, nearPlace.size());
    }

    public Set<Place> searchPlace(String keyword, HttpServletRequest request) {
        Set<Place> places;
        if (TextUtils.isTime(keyword)) {
            places = mPlaceDAO.searchWithTime(keyword, TextUtils.getTime(keyword));
        } else {
            places = mPlaceDAO.searchWithoutTime(keyword);
        }
        User user = UserUtil.getUserFromRequest(request, mUserDAO, mTokenUtil);
        if (user != null) {
            for (Place place : places) {
                place.setFavorite(mUserFavoriteDAO.isFavoritePlace(user, place));
            }
        }
        return places;
    }

    public ResponseEntity<?> likePlace(int placeId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status = false;
        if (!TextUtils.isEmpty(token)) {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            status = mUserFavoriteDAO.favoritePlace(placeId, user);
        }
        return ResponseEntity.ok(LikeTopicResponse.builder().status(status).build());
    }

    public Place getPlaceById(int placeId) {
        return mPlaceDAO.findByPlaceId(placeId);
    }

    public Page<Place> getLocationPlace(int locationId, int locationType, Pageable pageable, HttpServletRequest request) {
        Page<Place> places;
        if (locationType == LocationType.CITY) {
            places = mPlaceDAO.findAllByPlaceAddressProvinceCityCityId(locationId, pageable);
        } else if (locationType == LocationType.PROVINCE) {
            places = mPlaceDAO.findAllByPlaceAddressProvinceProvinceId(locationId, pageable);
        } else {
            return null;
        }
        User user = UserUtil.getUserFromRequest(request, mUserDAO, mTokenUtil);
        if (user != null) {
            for (Place place : places) {
                place.setFavorite(mUserFavoriteDAO.isFavoritePlace(user, place));
            }
        }
        return places;
    }

    public ResponseEntity<?> getPlaceByPlaceId(int placeId, HttpServletRequest request) {
        Place place = mPlaceDAO.findByPlaceId(placeId);
        boolean status = true;
        String message = null;
        if (place == null) {
            status = false;
            message = mMessageSource.getMessage("message.storeNotExist", null, request.getLocale());
        }
        return ResponseEntity.ok(FindStoreResponse.builder()
                .place(place)
                .message(message)
                .status(status)
                .build());
    }
}
