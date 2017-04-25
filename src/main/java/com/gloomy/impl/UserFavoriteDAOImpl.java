package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.User;
import com.gloomy.beans.UserFavorite;
import com.gloomy.dao.UserFavoriteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 15-Apr-17.
 */
@Service
public class UserFavoriteDAOImpl {
    private UserFavoriteDAO mUserFavoriteDAO;
    private PlaceDAOImpl mPlaceDAO;

    @Autowired
    public void setUserFavoriteDAO(UserFavoriteDAO mUserFavoriteDAO) {
        this.mUserFavoriteDAO = mUserFavoriteDAO;
    }

    @Autowired
    public void setPlaceDAO(PlaceDAOImpl mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    public boolean isFavoritePlace(User user, Place place) {
        return user != null && mUserFavoriteDAO.findUserFavoriteByUserUserIdAndPlacePlaceId(user.getUserId(), place.getPlaceId()) != null;
    }

    public boolean favoritePlace(int placeId, User user) {
        UserFavorite userFavorite = mUserFavoriteDAO.findUserFavoriteByUserUserIdAndPlacePlaceId(user.getUserId(), placeId);
        if (userFavorite != null) {
            mUserFavoriteDAO.delete(userFavorite);
            return true;
        } else {
            Place place = mPlaceDAO.getPlaceById(placeId);
            userFavorite = UserFavorite.builder()
                    .place(place)
                    .user(user)
                    .build();
            return mUserFavoriteDAO.save(userFavorite) != null;
        }
    }
}
