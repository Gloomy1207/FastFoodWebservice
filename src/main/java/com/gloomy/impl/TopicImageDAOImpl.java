package com.gloomy.impl;

import com.gloomy.beans.TopicImage;
import com.gloomy.dao.TopicImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Service
public class TopicImageDAOImpl {

    private TopicImageDAO mTopicImageDAO;

    @Autowired
    public void setTopicImageDAO(TopicImageDAO mTopicImageDAO) {
        this.mTopicImageDAO = mTopicImageDAO;
    }

    public Page<TopicImage> getImagesByTopicId(int topicId, Pageable pageable) {
        return mTopicImageDAO.findByTopicTopicId(topicId, pageable);
    }
}
