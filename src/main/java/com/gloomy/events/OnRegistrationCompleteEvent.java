package com.gloomy.events;

import com.gloomy.beans.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    private String baseUrl;

    public OnRegistrationCompleteEvent(String appUrl, Locale locale, User user, String baseUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.baseUrl = baseUrl;
    }
}
