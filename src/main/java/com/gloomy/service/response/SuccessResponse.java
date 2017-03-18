package com.gloomy.service.response;

import com.gloomy.beans.Exclude;
import com.gloomy.utils.JsonUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SuccessResponse {
    protected boolean status;
    protected String message;
    @Exclude
    protected int type;

    public String toJson() {
        message = getMessageByType(type);
        return JsonUtil.getInstance().objectToJson(this);
    }

    private String getMessageByType(int type) {
        switch (type) {
            case SuccessType.ADD_USER:
                return ResponseConstant.ADD_USER_SUCCESSFUL;
        }
        return "";
    }
}
