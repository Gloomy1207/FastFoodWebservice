package com.gloomy.dao;

import com.gloomy.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19/04/2017.
 */
@Transactional
public interface RoleDAO extends JpaRepository<Role, Integer> {

    Role findRoleByRoleValue(String roleValue);
}
