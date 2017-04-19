package com.gloomy.listeners;

import com.gloomy.beans.User;
import com.gloomy.beans.VerificationToken;
import com.gloomy.events.OnRegistrationCompleteEvent;
import com.gloomy.impl.VerificationDAOImpl;
import com.gloomy.service.ApiMappingUrl;
import com.gloomy.service.ApiParameter;
import com.gloomy.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationDAOImpl mVerificationDAO;

    private final JavaMailSender mMailSender;

    private final MessageSource mMessageSource;

    @Value("${spring.mail.username}")
    private String mEmailUsername;

    @Value("${spring.mail.password}")
    private String mEmailPassword;

    @Value("${spring.mail.host}")
    private String mEmailHost;

    @Value("${spring.mail.protocol}")
    private String mEmailProtocol;

    @Autowired
    public RegistrationListener(VerificationDAOImpl verificationDAO, JavaMailSender mailSender, MessageSource messageSource) {
        mVerificationDAO = verificationDAO;
        mMailSender = mailSender;
        mMessageSource = messageSource;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken token = mVerificationDAO.createVerificationToken(user);
        String recipientAddress = user.getEmail();
        String subject = "FastFood Registration Confirmation";
        String confirmationUrl = String.format("%s%s?%s=%s", event.getAppUrl(), ApiMappingUrl.REGISTRATION_CONFIRM, ApiParameter.CONFIRM_TOKEN, token.getToken());
        String messageSuccess = mMessageSource.getMessage("message.sendConfirmationSuccess", null, event.getLocale());
        Session session = Session.getDefaultInstance(PropertiesUtil.getMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mEmailUsername, mEmailPassword);
            }
        });
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mEmailUsername));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
            message.setSubject(subject);
            message.setText(String.format("%s \n%s%s", messageSuccess, event.getBaseUrl(), confirmationUrl));
            Transport transport = session.getTransport(mEmailProtocol);
            transport.connect(mEmailHost, mEmailUsername, mEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
