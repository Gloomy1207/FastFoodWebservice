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
@Table(name = "place")
@Setter
@Getter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private int placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "description")
    private String description;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Set<PlaceImage> placeImages;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Set<PlaceRating> placeRatings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_id")
    @JsonIgnore
    private PlaceType placeType;

    @Transient
    private String placeTypeName;

    @Transient
    private int placeTypeId;
}
