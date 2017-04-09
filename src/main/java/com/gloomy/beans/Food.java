package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gloomy.utils.LocationUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@Setter
@Getter
@Entity
@Table(name = "Food")
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "description")
    private String description;

    @Column(name = "recipe")
    private String recipe;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Set<FoodImage> foodImages;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Set<FoodRating> foodRatings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Set<FoodPrice> prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Set<PlaceFood> placeFoods;

    @Transient
    private float rating;
    @Transient
    private int numberOfRating;

    @Transient
    public float getFoodRating() {
        int total = 0;
        if (foodRatings.size() > 0) {
            for (FoodRating rating : foodRatings) {
                total += rating.getStar();
            }
            return total / foodRatings.size();
        }
        return total;
    }

    @Transient
    public List<PlaceFood> getNearPlace(LatLng latLng) {
        final int distanceAverage = 20 * 1000;
        List<PlaceFood> places = new ArrayList<>();
        for (PlaceFood placeFood : placeFoods) {
            if (placeFood.getPlace() != null && placeFood.getPlace().getPlaceAddress() != null) {
                LatLng placeLatLng = LatLng.builder()
                        .lat(placeFood.getPlace().getPlaceAddress().getLat())
                        .lng(placeFood.getPlace().getPlaceAddress().getLng()).build();
                if (LocationUtil.getDistanceBetweenTwoPoint(latLng, placeLatLng) <= distanceAverage) {
                    places.add(placeFood);
                }
            }
        }
        return places;
    }
}
