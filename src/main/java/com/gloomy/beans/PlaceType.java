package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Entity
@Table(name = "place_type")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_type_id")
    @JsonProperty("place_type_id")
    private int placeTypeId;

    @Column(name = "type_name")
    @JsonProperty("type_name")
    private String typeName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_id")
    @JsonIgnore
    private Set<Place> places;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "place_rating_type", joinColumns = {@JoinColumn(name = "place_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "rating_type_id")})
    @JsonProperty("rating_type")
    private Set<RatingType> ratingTypes;
}
