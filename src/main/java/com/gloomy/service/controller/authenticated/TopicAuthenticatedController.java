package com.gloomy.service.controller.authenticated;

import com.gloomy.impl.TopicCommentDAOImpl;
import com.gloomy.impl.TopicDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 25-Mar-17.
 */
@RestController
@RequestMapping(ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.TOPIC_ENDPOINT)
public class TopicAuthenticatedController {

    private final TopicDAOImpl mTopicDAO;
    private final TopicCommentDAOImpl mTopicCommentDAO;

    @Autowired
    public TopicAuthenticatedController(TopicDAOImpl mTopicDAO, TopicCommentDAOImpl mTopicCommentDAO) {
        this.mTopicDAO = mTopicDAO;
        this.mTopicCommentDAO = mTopicCommentDAO;
    }

    @GetMapping(value = ApiMappingUrl.LIKE)
    @ResponseBody
    public ResponseEntity<?> likeTopic(@RequestParam(value = ApiParameter.TOPIC_ID) int topicId,
                                       HttpServletRequest request) {
        return mTopicDAO.likeTopic(topicId, request);
    }

    @PostMapping(value = ApiMappingUrl.COMMENT)
    @ResponseBody
    public ResponseEntity<?> commentTopic(@RequestParam(value = ApiParameter.CONTENT) String content,
                                          @RequestParam(value = ApiParameter.TOPIC_ID) int topicId,
                                          HttpServletRequest request) {
        return mTopicCommentDAO.commentTopic(content, topicId, request);
    }

    @GetMapping(value = ApiMappingUrl.DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@RequestParam(value = ApiParameter.COMMENT_ID) int commentId,
                                           HttpServletRequest request) {
        return mTopicCommentDAO.deleteComment(commentId, request);
    }
}
