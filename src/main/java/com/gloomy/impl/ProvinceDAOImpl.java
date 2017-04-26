package com.gloomy.impl;

import com.gloomy.beans.Province;
import com.gloomy.dao.ProvinceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26-Apr-17.
 */
@Service
public class ProvinceDAOImpl {
    private ProvinceDAO mProvinceDAO;

    @Autowired
    public void setProvinceDAO(ProvinceDAO mProvinceDAO) {
        this.mProvinceDAO = mProvinceDAO;
    }

    public Set<Province> getProvinceByCityId(int cityId) {
        return mProvinceDAO.findAllByCityCityId(cityId);
    }
}
