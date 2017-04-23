package com.gloomy.impl;

import com.gloomy.beans.Topic;
import com.gloomy.beans.TopicComment;
import com.gloomy.beans.User;
import com.gloomy.dao.TopicCommentDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.security.UserAuthority;
import com.gloomy.security.UserRole;
import com.gloomy.service.controller.response.authenticated.DeleteTopicResponse;
import com.gloomy.service.controller.response.authenticated.TopicCommentResponse;
import com.gloomy.utils.JwtTokenUtil;
import com.gloomy.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Service
public class TopicCommentDAOImpl {

    private TopicCommentDAO mTopicCommentDAO;
    private UserDAOImpl mUserDAO;
    private JwtTokenUtil mTokenUtil;
    private TopicDAOImpl mTopicDAO;
    private MessageSource mMessageSource;

    @Autowired
    public void setTopicCommentDAO(TopicCommentDAO topicCommentDAO) {
        this.mTopicCommentDAO = topicCommentDAO;
    }

    @Autowired
    public void setUserDAO(UserDAOImpl mUserDAO) {
        this.mUserDAO = mUserDAO;
    }

    @Autowired
    public void setTokenUtil(JwtTokenUtil mTokenUtil) {
        this.mTokenUtil = mTokenUtil;
    }

    @Autowired
    public void setTopicDAO(TopicDAOImpl mTopicDAO) {
        this.mTopicDAO = mTopicDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public Page<TopicComment> getCommentByTopicId(int id, Pageable pageable, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        Page<TopicComment> topicComments = mTopicCommentDAO.findTopicCommentByTopicTopicIdOrderByPostTimeDesc(id, pageable);
        if (!TextUtils.isEmpty(token)) {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            for (TopicComment topicComment : topicComments) {
                topicComment.setAllowDelete(user.equals(topicComment.getUser()) || user.getRole().getRoleValue().equals(UserRole.ADMIN));
            }
        }
        return topicComments;
    }

    public ResponseEntity<?> commentTopic(String content, int topicId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status;
        if (TextUtils.isEmpty(token)) {
            status = false;
        } else {
            Topic topic = mTopicDAO.getTopicById(topicId);
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            TopicComment topicComment = TopicComment.builder()
                    .topic(topic)
                    .user(user)
                    .content(content)
                    .postTime(new Timestamp(System.currentTimeMillis()))
                    .build();
            status = mTopicCommentDAO.save(topicComment) != null;
        }
        return ResponseEntity.ok(TopicCommentResponse.builder().status(status).build());
    }

    public ResponseEntity<?> deleteComment(int commentId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status;
        String message = null;
        String requestPermissionMessage = mMessageSource.getMessage("message.requestPermission", null, request.getLocale());
        if (TextUtils.isEmpty(token)) {
            message = requestPermissionMessage;
            status = false;
        } else {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            if (user.getRole().getRoleValue().equals(UserRole.ADMIN)) {
                mTopicCommentDAO.delete(commentId);
                status = true;
            } else if (user.getRole().containAuthority(UserAuthority.DELETE_OWN) && isUserComment(user.getUserId(), commentId)) {
                mTopicCommentDAO.delete(commentId);
                status = true;
            } else {
                message = requestPermissionMessage;
                status = false;
            }
        }
        return ResponseEntity.ok(DeleteTopicResponse.builder()
                .message(message)
                .status(status)
                .build());
    }

    private boolean isUserComment(Long userId, int commentId) {
        return mTopicCommentDAO.findTopicCommentByCommentIdAndUserUserId(commentId, userId) != null;
    }
}
