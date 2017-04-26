package com.gloomy.dao;

import com.gloomy.beans.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26-Apr-17.
 */
@Transactional
public interface ProvinceDAO extends JpaRepository<Province, Integer> {

    Set<Province> findAllByCityCityId(int cityId);
}
