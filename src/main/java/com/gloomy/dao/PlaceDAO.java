package com.gloomy.dao;

import com.gloomy.beans.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Transactional
public interface PlaceDAO extends JpaRepository<Place, Integer> {
}
