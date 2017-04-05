package com.gloomy.dao;

import com.gloomy.beans.City;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 04/04/2017.
 */
@Transactional
public interface CityDAO extends JpaRepository<City, Integer> {
}
