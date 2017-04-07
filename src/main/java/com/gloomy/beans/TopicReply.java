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
@Table(name = "topic_reply")
@Getter
@Setter
public class TopicReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_reply_id")
    private int postReplyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_comment_id")
    @JsonIgnore
    private TopicComment topicComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_time")
    private Timestamp replyTime;
}
