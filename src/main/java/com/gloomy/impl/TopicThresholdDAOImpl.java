package com.gloomy.impl;

import com.gloomy.dao.TopicThresholdDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 10/04/2017.
 */
@Service
public class TopicThresholdDAOImpl {

    private TopicThresholdDAO mTopicThresholdDAO;

    @Autowired
    public void setTopicThresholdDAO(TopicThresholdDAO mTopicThresholdDAO) {
        this.mTopicThresholdDAO = mTopicThresholdDAO;
    }

    public int getThreshold(String topicTypeName) {
        return mTopicThresholdDAO.findThreshold(topicTypeName).getNumOfLike();
    }

    /**
     * Threshold TypeName definition
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface TopicTypeName {
        String HOT = "HOT";
        String TRENDING = "TRENDING";
        String FRESH = "FRESH";
    }
}
