package com.gloomy.dao;

import com.gloomy.beans.PlaceFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Apr-17.
 */
@Transactional
public interface PlaceFoodDAO extends JpaRepository<PlaceFood, Integer> {

    Page<PlaceFood> findAllByPlacePlaceId(int placeId, Pageable pageable);

    Page<PlaceFood> findAllByPlace_PlaceAddress_ProvinceProvinceId(int provinceId, Pageable pageable);

    Page<PlaceFood> findAllByPlace_PlaceAddress_Province_CityCityId(int cityId, Pageable pageable);

    @Query("SELECT pf FROM PlaceFood pf WHERE pf.price BETWEEN ?1 - 20000 AND ?1 + 20000")
    List<PlaceFood> search(double price);
}
