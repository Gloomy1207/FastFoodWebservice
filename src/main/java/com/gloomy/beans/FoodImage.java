package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 02-Apr-17.
 */
@Getter
@Setter
@Table(name = "food_image")
@Entity
public class FoodImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "image_path")
    @JsonProperty("image_path")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore
    private Food food;
}
