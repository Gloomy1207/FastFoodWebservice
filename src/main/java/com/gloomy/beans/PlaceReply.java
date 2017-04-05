package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 05/04/2017.
 */
@Entity
@Table(name = "place_reply")
@Getter
@Setter
public class PlaceReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_reply_id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_time")
    private Timestamp replyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_comment_id")
    @JsonIgnore
    private PlaceComment placeComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
