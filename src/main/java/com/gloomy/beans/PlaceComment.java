package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 05/04/2017.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "place_comment")
public class PlaceComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_comment_id")
    @JsonProperty("comment_id")
    private int commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_time")
    @JsonProperty("post_time")
    private Timestamp commentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    @JsonProperty("is_allow_delete")
    private boolean isAllowDelete;
}
