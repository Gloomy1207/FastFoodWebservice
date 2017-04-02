package com.gloomy.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
