package com.gloomy.dao;

import com.gloomy.beans.RatingType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26/04/2017.
 */
@Transactional
public interface RatingTypeDAO extends JpaRepository<RatingType, Integer> {

    RatingType findByRatingTypeId(int ratingTypeId);
}
