package com.gloomy.service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
public class ApiMappingUrl {
    /**
     * Base URL
     */
    public static final String API_BASE_URL = "/v1/{locale}/api";
    public static final String API_BASIC_URL = API_BASE_URL + "/basic";
    public static final String API_AUTH_URL = API_BASE_URL + "/auth";

    /**
     * Authorization URL
     */
    public static final String OAUTH_API_URL = "/v1/api/oauth";
    public static final String LOGIN_USER_API_URL = "/login";
    public static final String REFRESH_TOKEN_URL = "/refresh";
    public static final String LOGOUT_USER_API_URL = "/logout";
    public static final String REGISTER_USER_API_URL = "/register";

    /**
     * Endpoint URL
     */
    public static final String MY_PROFILE_ENDPOINT = "/profile";
    public static final String FOOD_ENDPOINT = "/food";
    public static final String PLACE_ENDPOINT = "/place";
    public static final String LOCATION_ENDPOINT = "/location";
    public static final String USER_ENDPOINT = "/user";

    /**
     * RestService For App URL
     */
    public static final String HOME = "/home";
}
