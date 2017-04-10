package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 05/04/2017.
 */
@Entity
@Table(name = "topic")
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "topic_id")
    private int postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "post_time")
    private Timestamp post_time;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Set<TopicImage> images;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    @JsonIgnore
    private Set<TopicLike> topicLikes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Set<TopicComment> comments;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Topic) {
            return ((Topic) obj).getPostId() == postId;
        }
        return super.equals(obj);
    }
}
