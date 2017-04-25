package com.gloomy.utils;

import com.gloomy.beans.User;
import com.gloomy.impl.UserDAOImpl;
import com.gloomy.security.SecurityConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Apr-17.
 */
public final class UserUtil {
    private static final String AVATAR_UPLOAD_DIRECTORY = "avatar";
    private static final String STATIC_IMAGES_RESOURCE_PATH = "images";
    private static final String EMPTY_AVATAR_NAME = "face_sample.png";

    private UserUtil() {
        // No-op
    }

    public static String getUserAvatarPath(User user, HttpServletRequest request) {
        String avatarPath = String.format("%s%s%s", ServerInformationUtil.getURLWithContextPath(request), "/", AVATAR_UPLOAD_DIRECTORY);
        if (TextUtils.isEmpty(user.getAvatar())) {
            return String.format("%s%s%s%s%s", ServerInformationUtil.getURLWithContextPath(request), "/", STATIC_IMAGES_RESOURCE_PATH, "/", EMPTY_AVATAR_NAME);
        } else if (user.getAvatar().contains("http://") || user.getAvatar().contains("https://")) {
            return user.getAvatar();
        }
        return String.format("%s%s%s", avatarPath, "/", user.getAvatar());
    }

    public static User getUserFromRequest(HttpServletRequest request, UserDAOImpl userDAO, JwtTokenUtil tokenUtil) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_NAME);
        if (!TextUtils.isEmpty(token)) {
            return userDAO.getUserByUsername(tokenUtil.getUsernameFromToken(token));
        }
        return null;
    }
}
