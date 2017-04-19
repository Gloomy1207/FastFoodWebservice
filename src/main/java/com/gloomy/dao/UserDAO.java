package com.gloomy.dao;

import com.gloomy.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Set;

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

    @Query("SELECT u FROM User u WHERE (u.email LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(u.username LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(u.fullname LIKE LOWER(CONCAT('%', ?1, '%') ) ) OR " +
            "(u.description LIKE LOWER(CONCAT('%', ?1, '%') ) )")
    Set<User> search(String keyword);

    @Query("SELECT u FROM User u ORDER BY u.point")
    Page<User> findRatingUser(Pageable pageable);


    @Query("SELECT u FROM User u WHERE (u.email = ?1 AND u.facebookId = ?2)")
    User findUserByFacebookIdAndAndEmail(String email, String facebookId);

    User findUserByEmail(String email);
}
