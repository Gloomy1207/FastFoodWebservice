package com.gloomy.utils;

import com.gloomy.service.ServiceExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
public class JsonUtil {
    private static JsonUtil sJsonUtil;

    private JsonUtil() {
        // No-op
    }

    public static JsonUtil getInstance() {
        if (sJsonUtil == null) {
            sJsonUtil = new JsonUtil();
        }
        return sJsonUtil;
    }

    public String objectToJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ServiceExclusionStrategy());
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public <T> T jsonToObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
