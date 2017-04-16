package com.gloomy.dao;

import com.gloomy.beans.PlaceRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Apr-17.
 */
@Transactional
public interface PlaceRatingDAO extends JpaRepository<PlaceRating, Integer> {

    @Query("SELECT p FROM PlaceRating p GROUP BY p.place.placeId ORDER BY SUM (p.star)")
    List<PlaceRating> findPlaceByOrderByRating(Pageable pageable);
}
