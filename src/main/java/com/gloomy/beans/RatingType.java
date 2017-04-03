package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 03-Apr-17.
 */
@Entity
@Table(name = "ratingtype")
@Getter
@Setter
public class RatingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_type_id")
    private int ratingTypeId;

    @Column(name = "rating_type_name")
    private String ratingTypeName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "place_rating_type", joinColumns = {@JoinColumn(name = "rating_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_type_id")})
    private Set<PlaceType> placeTypes;
}
