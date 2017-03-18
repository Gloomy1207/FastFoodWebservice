package com.gloomy.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 16-Mar-17.
 */
public final class StringUtil {
    private static StringUtil sInstance;

    private StringUtil() {
    }

    public static StringUtil getInstance() {
        if (sInstance == null) {
            sInstance = new StringUtil();
        }
        return sInstance;
    }

    public String convertStringToMd5(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String result = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(str.getBytes());
            result = new BigInteger(1, md.digest()).toString(16);
        }
        return result;
    }
}
