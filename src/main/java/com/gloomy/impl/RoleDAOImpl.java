package com.gloomy.impl;

import com.gloomy.beans.Role;
import com.gloomy.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19/04/2017.
 */
@Service
public class RoleDAOImpl {

    private RoleDAO mRoleDAO;

    @Autowired
    public void setRoleDAO(RoleDAO mRoleDAO) {
        this.mRoleDAO = mRoleDAO;
    }

    public Role getRoleByRoleName(String roleName) {
        return mRoleDAO.findRoleByRoleValue(roleName);
    }

    /**
     * RoleName definition
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoleName {
        String ADMIN = "ROLE_ADMIN";
        String USER = "ROLE_USER";
        String GUEST = "ROLE_GUEST";
    }
}
