package com.gloomy.dao;

import com.gloomy.beans.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25/04/2017.
 */
@Transactional
public interface UserFavoriteDAO extends JpaRepository<UserFavorite, Integer> {

    UserFavorite findUserFavoriteByUserUserIdAndPlacePlaceId(Long userId, int placeId);
}
