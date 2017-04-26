package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Entity
@Table(name = "place")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    @JsonProperty("place_id")
    private int placeId;

    @Column(name = "place_name")
    @JsonProperty("place_name")
    private String placeName;

    @Column(name = "description")
    private String description;

    @Column(name = "open_time")
    @JsonProperty("open_time")
    private Time openTime;

    @Column(name = "close_time")
    @JsonProperty("close_time")
    private Time closeTime;

    @Column(name = "main_image")
    @JsonProperty("main_image")
    private String mainImage;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonProperty("place_images")
    private Set<PlaceImage> placeImages;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Set<PlaceRating> placeRatings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_id")
    @JsonProperty("place_type")
    private PlaceType placeType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    @JsonProperty("place_address")
    private PlaceAddress placeAddress;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "user_favorite", joinColumns = {@JoinColumn(name = "place_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Set<FoodPrice> prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Set<PlaceComment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Set<PlaceFood> foods;

    @Transient
    @JsonProperty("is_favorite")
    private boolean isFavorite;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Place) {
            return placeId == ((Place) obj).getPlaceId();
        }
        return super.equals(obj);
    }

    @JsonProperty("average_rating")
    public float getAverageRating() {
        if (!placeRatings.isEmpty()) {
            float point = 0;
            for (PlaceRating placeRating : placeRatings) {
                point += placeRating.getStar();
            }
            return (Math.round(point / placeRatings.size() * 100)) / 100.0f;
        }
        return 5;
    }

    @JsonProperty("number_rating")
    public String getNumberRating() {
        StringBuilder builder = new StringBuilder(String.valueOf(placeRatings.size())).append(" reviewer");
        if (placeRatings.size() > 1) {
            builder.append("s");
        }
        return builder.toString();
    }
}
