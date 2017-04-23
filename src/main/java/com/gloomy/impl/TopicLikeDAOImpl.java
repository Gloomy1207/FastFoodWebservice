package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.beans.TopicLike;
import com.gloomy.beans.User;
import com.gloomy.dao.TopicLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 15-Apr-17.
 */
@Service
public class TopicLikeDAOImpl {
    private TopicLikeDAO mTopicLikeDAO;
    private TopicDAOImpl mTopicDAO;

    @Autowired
    public void setTopicLikeDAO(TopicLikeDAO mTopicLikeDAO) {
        this.mTopicLikeDAO = mTopicLikeDAO;
    }

    @Autowired
    public void setTopicDAO(TopicDAOImpl mTopicDAO) {
        this.mTopicDAO = mTopicDAO;
    }

    public boolean isLikeTopic(User user, Topic topic) {
        return user != null && mTopicLikeDAO.getTopicLikeByTopicAndUser(topic.getTopicId(), user.getUserId()) != null;
    }

    public boolean likeTopic(int topicId, User user) {
        TopicLike topicLike = mTopicLikeDAO.findTopicLikeByUserUserIdAndTopicTopicId(user.getUserId(), topicId);
        if (topicLike != null) {
            mTopicLikeDAO.delete(topicLike);
            return true;
        } else {
            Topic topic = mTopicDAO.getTopicById(topicId);
            topicLike = TopicLike.builder()
                    .likeTime(new Timestamp(System.currentTimeMillis()))
                    .topic(topic)
                    .user(user)
                    .build();
            return mTopicLikeDAO.save(topicLike) != null;
        }
    }
}
