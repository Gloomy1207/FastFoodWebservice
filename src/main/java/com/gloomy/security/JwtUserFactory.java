package com.gloomy.security;

import com.gloomy.beans.User;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getFullname(),
                user.getRole());
    }
}
