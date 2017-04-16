package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.dao.TopicDAO;
import com.gloomy.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Service
public class TopicDAOImpl {
    private TopicDAO mTopicDAO;
    private TopicLikeDAOImpl mTopicLikeDAO;

    @Autowired
    public void setTopicDAO(TopicDAO topicDAO) {
        this.mTopicDAO = topicDAO;
    }

    @Autowired
    public void setTopicLikeDAO(TopicLikeDAOImpl mTopicLikeDAO) {
        this.mTopicLikeDAO = mTopicLikeDAO;
    }

    public Page<Topic> getAllOrderByLike(Pageable pageable, User user, HttpServletRequest request) {
        Page<Topic> topics;
        if (user == null) {
            topics = mTopicDAO.findAllOrderByLike(pageable);
        } else {
            topics = mTopicDAO.findAllOrderByLike(pageable);
            for (Topic topic : topics) {
                topic.setLike(mTopicLikeDAO.isLikeTopic(user, topic));
            }
        }
        for (Topic topic : topics) {
            topic.getUser().setAvatar(UserUtil.getUserAvatarPath(topic.getUser(), request));
        }
        return topics;
    }

    public Set<Topic> searchTopic(String keyword) {
        return mTopicDAO.search(keyword);
    }

    public Page<Topic> getTopicByThreshold(Pageable pageable, int threshold) {
        return mTopicDAO.findTopic(threshold, pageable);
    }

    public Page<Topic> getTopicByRandom(Pageable pageable) {
        return mTopicDAO.findTopicOrderRandom(pageable);
    }
}
