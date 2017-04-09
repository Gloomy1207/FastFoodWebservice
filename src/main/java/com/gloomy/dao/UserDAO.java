package com.gloomy.dao;

import com.gloomy.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */
@Transactional
public interface UserDAO extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findUserByUsername(String username);

    @Query("SELECT u FROM  User u ORDER BY u.point")
    Page<User> findUserOrderByPoint(Pageable pageable);
}
