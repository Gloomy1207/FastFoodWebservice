package com.gloomy.dao;

import com.gloomy.beans.FoodPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Transactional
public interface FoodPriceDAO extends JpaRepository<FoodPrice, Integer> {

    @Query("SELECT fp FROM FoodPrice fp WHERE fp.price BETWEEN ?1 - 20000 AND ?1 + 20000")
    List<FoodPrice> search(double price);
}
