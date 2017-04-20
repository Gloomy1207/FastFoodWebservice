package com.gloomy.utils;

import java.util.Properties;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 20-Apr-17.
 */
public class PropertiesUtil {
    private PropertiesUtil() {
        // No-op
    }

    public static Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "false");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        return properties;
    }
}
