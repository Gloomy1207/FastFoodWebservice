package com.gloomy.dao;

import com.gloomy.beans.PlaceImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 27-Apr-17.
 */
@Transactional
public interface PlaceImageDAO extends JpaRepository<PlaceImage, Integer> {

    Page<PlaceImage> findByPlacePlaceId(int placeId, Pageable pageable);
}
