package com.gloomy.service.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 26/04/2017.
 */
@Getter
@Setter
public class PlaceRatingRequest {
    @JsonProperty("list_rating")
    private List<PlaceRatingItem> placeRatingItems;
    @JsonProperty("place_id")
    private int placeId;

    /**
     * PlaceRatingItem
     */
    @Getter
    public static class PlaceRatingItem {
        @JsonProperty("rating_type_id")
        private int ratingTypeId;
        private float value;
    }
}
