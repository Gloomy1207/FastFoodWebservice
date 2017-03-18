package com.gloomy.service.response;

import com.gloomy.utils.JsonUtil;
import lombok.Builder;
import lombok.Getter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
@Getter
@Builder
public class FailureResponse {
    protected boolean status = false;
    protected String message;

    public String toJson() {
        return JsonUtil.getInstance().objectToJson(this);
    }

    private String getMessageByType(int type) {
        switch (type) {
            case FailureType.USER_EXIST:
                return ResponseConstant.USER_EXIST_MESSAGE_EN;
            case FailureType.SERVER_ERROR:
                return ResponseConstant.SERVER_ERROR_MESSAGE_EN;
            case FailureType.USERNAME_EMPTY:
                return ResponseConstant.USERNAME_EMPTY_MESSAGE_EN;
            case FailureType.PASSWORD_EMPTY:
                return ResponseConstant.PASSWORD_EMPTY_MESSAGE_EN;
        }
        return "";
    }

    public void addMessageFailure(int... types) {
        if (message == null) {
            message = "";
        }
        StringBuilder builder = new StringBuilder(message);
        int index = 0;
        for (int type : types) {
            builder.append(getMessageByType(type));
            if (index != types.length - 1) {
                builder.append("\n");
            }
            index++;
        }
        message = builder.toString();
    }
}
