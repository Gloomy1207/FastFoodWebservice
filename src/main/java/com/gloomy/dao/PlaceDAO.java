package com.gloomy.dao;

import com.gloomy.beans.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Transactional
public interface PlaceDAO extends JpaRepository<Place, Integer> {

    @Query("SELECT p FROM Place p WHERE (p.description LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(p.placeName LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(?2 BETWEEN p.openTime AND p.closeTime)")
    Set<Place> searchWithTime(String keyword, Time time);

    @Query("SELECT p FROM Place p WHERE (p.description LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(p.placeName LIKE LOWER(CONCAT('%', ?1, '%') ) )")
    Set<Place> searchWithoutTime(String keyword);

    @Query("SELECT p FROM Place p")
    Page<Place> findPlaceRating(Pageable pageable);
}
