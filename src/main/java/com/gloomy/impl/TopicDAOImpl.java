package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.beans.User;
import com.gloomy.dao.TopicDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.service.controller.response.authenticated.LikeTopicResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import com.gloomy.utils.UserUtil;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private UserDAOImpl mUserDAO;
    private JwtTokenUtil mTokenUtil;

    @Autowired
    public void setTopicDAO(TopicDAO topicDAO) {
        this.mTopicDAO = topicDAO;
    }

    @Autowired
    public void setTopicLikeDAO(TopicLikeDAOImpl mTopicLikeDAO) {
        this.mTopicLikeDAO = mTopicLikeDAO;
    }

    @Autowired
    public void setUserDAO(UserDAOImpl mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Autowired
    public void setTokenUtil(JwtTokenUtil mTokenUtil) {
        this.mTokenUtil = mTokenUtil;
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

    public Page<Topic> getTopicByThreshold(Pageable pageable, int threshold, HttpServletRequest request) {
        Page<Topic> topics = mTopicDAO.findTopic(threshold, pageable);
        User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(request.getHeader(SecurityConstants.TOKEN_HEADER_NAME)));
        for (Topic topic : topics) {
            topic.getUser().setAvatar(UserUtil.getUserAvatarPath(topic.getUser(), request));
            topic.setLike(mTopicLikeDAO.isLikeTopic(user, topic));
        }
        return topics;
    }

    public Page<Topic> getTopicByRandom(Pageable pageable, HttpServletRequest request) {
        Page<Topic> topics = mTopicDAO.findTopicOrderRandom(pageable);
        for (Topic topic : topics) {
            topic.getUser().setAvatar(UserUtil.getUserAvatarPath(topic.getUser(), request));
        }
        return topics;
    }

    public ResponseEntity<?> likeTopic(int topicId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status = false;
        if (!TextUtils.isEmpty(token)) {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            status = mTopicLikeDAO.likeTopic(topicId, user);
        }
        return ResponseEntity.ok(LikeTopicResponse.builder().status(status).build());
    }

    public Topic getTopicById(int topicId) {
        return mTopicDAO.findByTopicId(topicId);
    }
}