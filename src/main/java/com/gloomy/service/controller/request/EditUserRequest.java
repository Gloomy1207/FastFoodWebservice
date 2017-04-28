package com.gloomy.service.controller.request;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 28-Apr-17.
 */
@Getter
public class EditUserRequest {
    private String username;
    @SerializedName("full_name")
    private String fullName;
    private String email;
    private String description;
    private String avatar;
    private String password;
}
