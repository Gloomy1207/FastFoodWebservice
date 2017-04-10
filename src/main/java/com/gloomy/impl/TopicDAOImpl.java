package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.dao.TopicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Service
public class TopicDAOImpl {
    private TopicDAO mTopicDAO;

    @Autowired
    public void setTopicDAO(TopicDAO topicDAO) {
        this.mTopicDAO = topicDAO;
    }

    public Page<Topic> getAllOrderByLike(Pageable pageable) {
        return mTopicDAO.findAllOrderByLike(pageable);
    }

    public Set<Topic> searchTopic(String keyword) {
        return mTopicDAO.search(keyword);
    }
}
