package com.fanfiction.webproject.security;

import com.fanfiction.webproject.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }

}