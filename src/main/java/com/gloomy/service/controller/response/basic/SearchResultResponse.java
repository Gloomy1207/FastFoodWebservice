package com.gloomy.service.controller.response.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomy.beans.Food;
import com.gloomy.beans.Place;
import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.service.controller.response.ResponseParameters;
import lombok.Builder;

import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Builder
public class SearchResultResponse {
    @JsonProperty(ResponseParameters.SUCCESS)
    private boolean isSuccess;

    @JsonProperty(ResponseParameters.STORES)
    private Set<Place> stores;

    @JsonProperty(ResponseParameters.FOODS)
    private Set<Food> foods;

    @JsonProperty(ResponseParameters.PEOPLES)
    private Set<User> peoples;

    @JsonProperty(ResponseParameters.TOPICS)
    private Set<Topic> topics;
}
