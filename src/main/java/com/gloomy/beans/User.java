package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Set<FoodRating> foodRating;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<PlaceRating> placeRatings;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "email")
    private String email;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "point")
    private int point;
    @Column(name = "avatar")
    private String avatar;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favorite", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_id")})
    private Set<Place> places;
}
