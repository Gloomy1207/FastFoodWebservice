package com.gloomy.service.controller.authenticated;

import com.gloomy.impl.PlaceCommentDAOImpl;
import com.gloomy.impl.PlaceDAOImpl;
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
@RequestMapping(ApiMappingUrl.API_AUTH_URL + ApiMappingUrl.PLACE_ENDPOINT)
public class StoreAuthenticatedController {

    private final PlaceDAOImpl mPlaceDAO;
    private final PlaceCommentDAOImpl mPlaceCommentDAO;

    @Autowired
    public StoreAuthenticatedController(PlaceDAOImpl mPlaceDAO, PlaceCommentDAOImpl mPlaceCommentDAO) {
        this.mPlaceDAO = mPlaceDAO;
        this.mPlaceCommentDAO = mPlaceCommentDAO;
    }

    @GetMapping(value = ApiMappingUrl.LIKE)
    @ResponseBody
    public ResponseEntity<?> likePlace(@RequestParam(value = ApiParameter.PLACE_ID) int placeId,
                                       HttpServletRequest request) {
        return mPlaceDAO.likePlace(placeId, request);
    }

    @PostMapping(value = ApiMappingUrl.COMMENT)
    @ResponseBody
    public ResponseEntity<?> commentTopic(@RequestParam(value = ApiParameter.CONTENT) String content,
                                          @RequestParam(value = ApiParameter.PLACE_ID) int placeId,
                                          HttpServletRequest request) {
        return mPlaceCommentDAO.commentPlace(content, placeId, request);
    }

    @GetMapping(value = ApiMappingUrl.DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@RequestParam(value = ApiParameter.COMMENT_ID) int commentId,
                                           HttpServletRequest request) {
        return mPlaceCommentDAO.deleteComment(commentId, request);
    }
}
