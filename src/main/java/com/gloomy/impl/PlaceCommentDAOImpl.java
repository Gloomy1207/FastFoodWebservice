package com.gloomy.impl;

import com.gloomy.beans.Place;
import com.gloomy.beans.PlaceComment;
import com.gloomy.beans.User;
import com.gloomy.dao.PlaceCommentDAO;
import com.gloomy.security.SecurityConstants;
import com.gloomy.security.UserAuthority;
import com.gloomy.service.controller.response.authenticated.DeletePlaceCommentResponse;
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
 * Created by Gloomy on 25/04/2017.
 */
@Service
public class PlaceCommentDAOImpl {

    private PlaceCommentDAO mPlaceCommentDAO;
    private PlaceDAOImpl mPlaceDAO;
    private UserDAOImpl mUserDAO;
    private JwtTokenUtil mTokenUtil;
    private MessageSource mMessageSource;

    @Autowired
    public void setPlaceCommentDAO(PlaceCommentDAO mPlaceCommentDAO) {
        this.mPlaceCommentDAO = mPlaceCommentDAO;
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
    public void setPlaceDAO(PlaceDAOImpl mPlaceDAO) {
        this.mPlaceDAO = mPlaceDAO;
    }

    @Autowired
    public void setMessageSource(MessageSource mMessageSource) {
        this.mMessageSource = mMessageSource;
    }

    public Page<PlaceComment> getCommentByPlaceId(int placeId, Pageable pageable, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        Page<PlaceComment> placeComments = mPlaceCommentDAO.findByPlacePlaceIdOrderByCommentTimeDesc(placeId, pageable);
        if (!TextUtils.isEmpty(token)) {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            for (PlaceComment comment : placeComments) {
                comment.setAllowDelete(comment.getUser().equals(user) || user.isAdmin());
            }
        }
        return placeComments;
    }

    public ResponseEntity<?> commentPlace(String content, int placeId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status;
        if (TextUtils.isEmpty(token)) {
            status = false;
        } else {
            Place place = mPlaceDAO.getPlaceById(placeId);
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            PlaceComment placeComment = PlaceComment.builder()
                    .user(user)
                    .content(content)
                    .commentTime(new Timestamp(System.currentTimeMillis()))
                    .place(place)
                    .build();
            status = mPlaceCommentDAO.save(placeComment) != null;
        }
        return ResponseEntity.ok(TopicCommentResponse.builder().status(status).build());
    }

    public ResponseEntity<?> deleteComment(int commentId, HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        boolean status;
        String message = null;
        String requestPermissionMessage = mMessageSource.getMessage("message.requestPermission", null, request.getLocale());
        //noinspection Duplicates
        if (TextUtils.isEmpty(token)) {
            message = requestPermissionMessage;
            status = false;
        } else {
            User user = mUserDAO.getUserByUsername(mTokenUtil.getUsernameFromToken(token));
            if (user.isAdmin()) {
                mPlaceCommentDAO.delete(commentId);
                status = true;
            } else if (user.getRole().containAuthority(UserAuthority.DELETE_OWN) && isUserComment(user.getUserId(), commentId)) {
                mPlaceCommentDAO.delete(commentId);
                status = true;
            } else {
                message = requestPermissionMessage;
                status = false;
            }
        }
        return ResponseEntity.ok(DeletePlaceCommentResponse.builder()
                .message(message)
                .status(status)
                .build());

    }

    private boolean isUserComment(Long userId, int commentId) {
        return mPlaceCommentDAO.findPlaceCommentByCommentIdAndUserUserId(commentId, userId) != null;
    }
}
