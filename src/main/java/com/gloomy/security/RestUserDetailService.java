package com.gloomy.security;

import com.gloomy.beans.User;
import com.gloomy.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Component
public class RestUserDetailService implements UserDetailsService {

    private final UserDAO mUserDAO;

    @Autowired
    public RestUserDetailService(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mUserDAO.findUserByUsername(username);
        return JwtUserFactory.create(user);
    }
}
