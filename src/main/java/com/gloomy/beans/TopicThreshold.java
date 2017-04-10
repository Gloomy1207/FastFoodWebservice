package com.gloomy.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Entity
@Getter
@Setter
@Table(name = "topic_threshold")
public class TopicThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "num_of_like")
    private int numOfLike;
}
