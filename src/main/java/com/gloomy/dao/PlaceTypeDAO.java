package com.gloomy.dao;

import com.gloomy.beans.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Transactional
public interface PlaceTypeDAO extends JpaRepository<PlaceType, Integer> {
    Set<PlaceType> findPlaceTypeByTypeNameIsContaining(String type);
}
