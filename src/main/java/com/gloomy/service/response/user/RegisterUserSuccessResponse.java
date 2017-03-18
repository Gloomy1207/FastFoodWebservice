package com.gloomy.service.response.user;

import com.gloomy.service.response.SuccessResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
public class RegisterUserSuccessResponse extends SuccessResponse {
    @SerializedName("access_token")
    private String accessToken;

    @Builder
    public RegisterUserSuccessResponse(boolean status, String message, int type, String accessToken) {
        super(status, message, type);
        this.accessToken = accessToken;
    }
}
