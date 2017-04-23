package com.gloomy.service.controller.basic;

import com.gloomy.beans.Topic;
import com.gloomy.beans.TopicComment;
import com.gloomy.beans.User;
import com.gloomy.impl.*;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_BASIC_URL + ApiMappingUrl.TOPIC_ENDPOINT)
public class TopicBasicRestController {
    private final TopicDAOImpl mTopicDAOImpl;
    private final TopicThresholdDAOImpl mTopicThresholdDAO;
    private final JwtTokenUtil mJwtTokenUtil;
    private final UserDAOImpl mUserDAO;
    private final TopicCommentDAOImpl mTopicCommentDAO;

    @Autowired
    public TopicBasicRestController(TopicDAOImpl mTopicDAOImpl, TopicThresholdDAOImpl mTopicThresholdDAO, JwtTokenUtil mJwtTokenUtil, UserDAOImpl mUserDAO, TopicCommentDAOImpl mTopicCommentDAO) {
        this.mTopicDAOImpl = mTopicDAOImpl;
        this.mTopicThresholdDAO = mTopicThresholdDAO;
        this.mJwtTokenUtil = mJwtTokenUtil;
        this.mUserDAO = mUserDAO;
        this.mTopicCommentDAO = mTopicCommentDAO;
    }

    @GetMapping(ApiMappingUrl.SEARCH)
    public Page<Topic> getMostLikeTopic(Pageable pageable, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        User user = null;
        if (!TextUtils.isEmpty(token)) {
            user = mUserDAO.getUserByUsername(mJwtTokenUtil.getUsernameFromToken(token));
        }
        return mTopicDAOImpl.getAllOrderByLike(pageable, user, request);
    }

    @GetMapping(ApiMappingUrl.HOT)
    public Page<Topic> getHotTopic(Pageable pageable, HttpServletRequest request) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.HOT), request);
    }

    @GetMapping(ApiMappingUrl.TRENDING)
    public Page<Topic> getTrendingTopic(Pageable pageable, HttpServletRequest request) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.TRENDING), request);
    }

    @GetMapping(ApiMappingUrl.FRESH)
    public Page<Topic> getFreshTopic(Pageable pageable, HttpServletRequest request) {
        return mTopicDAOImpl.getTopicByThreshold(pageable, mTopicThresholdDAO.getThreshold(TopicThresholdDAOImpl.TopicTypeName.FRESH), request);
    }

    @GetMapping(ApiMappingUrl.RANDOM)
    public Page<Topic> getRandomTopic(Pageable pageable, HttpServletRequest request) {
        return mTopicDAOImpl.getTopicByRandom(pageable, request);
    }

    @GetMapping(ApiMappingUrl.COMMENT)
    public Page<TopicComment> getTopicComment(@RequestParam(ApiParameter.TOPIC_ID) int topicId,
                                              Pageable pageable,
                                              HttpServletRequest request) {
        return mTopicCommentDAO.getCommentByTopicId(topicId, pageable, request);
    }
}
