package com.gloomy.configs;

import com.gloomy.utils.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(PropertiesUtil.getMailProperties());
        return javaMailSender;
    }
}
