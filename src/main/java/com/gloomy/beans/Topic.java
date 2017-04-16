package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 05/04/2017.
 */
@Entity
@Table(name = "topic")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "topic_id")
    @JsonProperty("topic_id")
    private int topicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "post_time")
    @JsonProperty("post_time")
    private Timestamp postTime;

    @Column(name = "title")
    private String title;

    @Column(name = "main_image")
    @JsonProperty("main_image")
    private String mainImage;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Set<TopicImage> images;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    @JsonIgnore
    private Set<TopicLike> topicLikes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    @JsonIgnore
    private Set<TopicComment> comments;

    @JsonProperty("count_topic_likes")
    public int getTopicLikesNumber() {
        return topicLikes.size();
    }

    @JsonProperty("count_topic_comments")
    public int getTopicCommentNumber() {
        return comments.size();
    }

    @Transient
    @JsonProperty("is_like")
    public boolean isLike;

    @JsonProperty("latest_comment")
    public TopicComment getLatestComment() {
        List<TopicComment> topicComments = new ArrayList<>(comments);
        if (!comments.isEmpty()) {
            return Collections.max(topicComments, Comparator.comparing(TopicComment::getPostTime));
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Topic) {
            return ((Topic) obj).getTopicId() == topicId;
        }
        return super.equals(obj);
    }
}
