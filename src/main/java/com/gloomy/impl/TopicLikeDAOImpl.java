package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.dao.TopicLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 15-Apr-17.
 */
@Service
public class TopicLikeDAOImpl {
    private TopicLikeDAO mTopicLikeDAO;

    @Autowired
    public void setTopicLikeDAO(TopicLikeDAO mTopicLikeDAO) {
        this.mTopicLikeDAO = mTopicLikeDAO;
    }

    public boolean isLikeTopic(User user, Topic topic) {
        return mTopicLikeDAO.getTopicLikeByTopicAndUser(topic.getTopicId(), user.getUserId()) != null;
    }
}
