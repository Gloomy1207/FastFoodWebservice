package com.gloomy.service.controller.response.basic;

import com.gloomy.beans.PlaceComment;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Getter
@Builder
public class PlaceCommentResponse {
    private boolean status;
    private String message;
    @SerializedName("store_comment_pageable")
    private Page<PlaceComment> placeComments;
}
