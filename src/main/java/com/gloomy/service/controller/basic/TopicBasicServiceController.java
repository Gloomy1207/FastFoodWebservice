package com.gloomy.service.controller.basic;

import com.gloomy.beans.Topic;
import com.gloomy.impl.TopicDAOImpl;
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
public class TopicBasicServiceController {
    private final TopicDAOImpl mTopicDAOImpl;

    @Autowired
    public TopicBasicServiceController(TopicDAOImpl mTopicDAOImpl) {
        this.mTopicDAOImpl = mTopicDAOImpl;
    }

    @GetMapping
    @RequestMapping(ApiMappingUrl.SEARCH)
    public Page<Topic> getMostLikeTopic(Pageable pageable) {
        return mTopicDAOImpl.getAllOrderByLike(pageable);
    }
}
