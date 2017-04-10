package com.gloomy.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 18-Mar-17.
 */
public final class TextUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isTime(String str) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static Time getTime(String str) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        dateFormat.setLenient(false);
        try {
            return new Time(dateFormat.parse(str).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
