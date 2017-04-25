package com.gloomy.service.controller.response.authenticated;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 23-Apr-17.
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteTopicCommentResponse {
    private boolean status;
    private String message;
}
