package com.gloomy.service.controller.basic;

import com.gloomy.beans.Topic;
import com.gloomy.impl.TopicDAOImpl;
import com.gloomy.impl.TopicThresholdDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.TOPIC_ENDPOINT)
public class TopicBasicRestController {
    private final TopicDAOImpl mTopicDAOImpl;
    private final TopicThresholdDAOImpl mTopicThresholdDAO;

    @Autowired
    public TopicBasicRestController(TopicDAOImpl mTopicDAOImpl, TopicThresholdDAOImpl mTopicThresholdDAO) {
        this.mTopicDAOImpl = mTopicDAOImpl;
        this.mTopicThresholdDAO = mTopicThresholdDAO;
    }

    @GetMapping(ApiMappingUrl.SEARCH)
    public Page<Topic> getMostLikeTopic(Pageable pageable) {
        return mTopicDAOImpl.getAllOrderByLike(pageable);
    }

    @GetMapping(ApiMappingUrl.HOT)
    public Page<Topic> getHotTopic(Pageable pageable) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.HOT));
    }

    @GetMapping(ApiMappingUrl.TRENDING)
    public Page<Topic> getTrendingTopic(Pageable pageable) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.TRENDING));
    }

    @GetMapping(ApiMappingUrl.FRESH)
    public Page<Topic> getFreshTopic(Pageable pageable) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.FRESH));
    }

    @GetMapping(ApiMappingUrl.RANDOM)
    public Page<Topic> getRandomTopic(Pageable pageable) {
        return mTopicDAOImpl.getTopicByRandom(pageable);
    }
}
