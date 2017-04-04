package com.gloomy.utils;

import com.gloomy.security.JwtUser;
import com.gloomy.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@Component
public final class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 8505288866703926477L;

    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_EMAIL = "email";
    private static final String CLAIM_KEY_URL = "url";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${security.secretKey}")
    private String mSecret;

    @Value("${security.expirationTime}")
    private Long mExpirationTime;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
            if (TextUtils.isEmpty(username)) {
                username = (String) claims.get(CLAIM_KEY_USERNAME);
            }
        } catch (Exception e) {
            System.out.println("getUsernameFromToken " + e.getMessage());
            username = null;
        }
        return username;
    }

    public String getEmailFromToken(String token) {
        return (String) getClaimsFromToken(token).get(CLAIM_KEY_EMAIL);
    }

    public String getUrlFromToken(String token) {
        return (String) getClaimsFromToken(token).get(CLAIM_KEY_URL);
    }

    public String getCreatedDateFromToken(String token) {
        return (String) getClaimsFromToken(token).get(CLAIM_KEY_CREATED);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        if (token.contains(SecurityConstants.TOKEN_PREFIX)) {
            token = token.substring(SecurityConstants.TOKEN_PREFIX.length());
        }
        try {
            claims = Jwts.parser()
                    .setSigningKey(mSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("getClaimsFromToken" + e.getMessage());
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + mExpirationTime * 1000);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, mSecret)
                .compact();
    }

    public String generateToken(String email, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_EMAIL, email);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token) || ignoreTokenExpiration(token);
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}
