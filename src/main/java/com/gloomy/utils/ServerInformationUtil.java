package com.gloomy.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 14/04/2017.
 */
public final class ServerInformationUtil {
    private ServerInformationUtil() {
    }

    public static String getURLWithContextPath(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (serverName.contains("localhost")) {
            try {
                serverName = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return request.getScheme() + "://" + serverName + ":" + request.getServerPort() + request.getContextPath();
    }
}
