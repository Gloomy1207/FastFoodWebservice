package com.gloomy.dao;

import com.gloomy.beans.FoodRating;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Transactional
public interface FoodRatingDAO extends JpaRepository<FoodRating, Integer> {
}
