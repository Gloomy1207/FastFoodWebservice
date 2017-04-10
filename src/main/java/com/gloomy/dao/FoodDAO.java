package com.gloomy.dao;

import com.gloomy.beans.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@Transactional
public interface FoodDAO extends JpaRepository<Food, Integer> {

    @Query("SELECT f FROM Food f WHERE (f.foodName LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(f.description LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(f.recipe LIKE LOWER(CONCAT('%', ?1, '%') ) )")
    Set<Food> search(String keyword);
}
