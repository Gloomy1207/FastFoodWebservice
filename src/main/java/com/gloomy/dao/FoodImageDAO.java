package com.gloomy.dao;

import com.gloomy.beans.FoodImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 21-Apr-17.
 */
@Transactional
public interface FoodImageDAO extends JpaRepository<FoodImage, Integer> {

    Page<FoodImage> findByFood_FoodId(int foodId, Pageable pageable);
}
