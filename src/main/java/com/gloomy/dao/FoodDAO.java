package com.gloomy.dao;

import com.gloomy.beans.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@Transactional
public interface FoodDAO extends JpaRepository<Food, Integer> {
}
