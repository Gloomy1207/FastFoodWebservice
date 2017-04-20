package com.gloomy.impl;

import com.gloomy.beans.User;
import com.gloomy.beans.VerificationToken;
import com.gloomy.dao.VerificationTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 19-Apr-17.
 */
@Service
public class VerificationDAOImpl {
    public static final int EXPIRATION = 60 * 24;

    private VerificationTokenDAO mVerificationTokenDAO;

    @Autowired
    public void setVerificationTokenDAO(VerificationTokenDAO mVerificationTokenDAO) {
        this.mVerificationTokenDAO = mVerificationTokenDAO;
    }

    public VerificationToken createVerificationToken(User user) {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (mVerificationTokenDAO.findByToken(token) != null);
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(calculateExpiryDate(EXPIRATION))
                .build();
        return mVerificationTokenDAO.save(verificationToken);
    }

    public String recreateVerificationToken() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (mVerificationTokenDAO.findByToken(token) != null);
        return token;
    }

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public VerificationToken getVerificationToken(String token) {
        return mVerificationTokenDAO.findByToken(token);
    }

    public boolean isTokenExpired(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0;
    }

    public VerificationToken getVerificationTokenByUser(User user) {
        return mVerificationTokenDAO.findByUser(user);
    }

    public void updateValue(VerificationToken verificationToken) {
        mVerificationTokenDAO.save(verificationToken);
    }

    public void deleteItem(VerificationToken verificationToken) {
        mVerificationTokenDAO.delete(verificationToken);
    }
}
