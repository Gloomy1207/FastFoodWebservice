package com.gloomy.dao;

import com.gloomy.beans.User;
import com.gloomy.beans.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Transactional
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
