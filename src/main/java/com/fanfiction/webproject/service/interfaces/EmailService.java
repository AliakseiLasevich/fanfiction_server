package com.fanfiction.webproject.service.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface EmailService {

    void sendVerificationEmailToken(String email, String emailVerificationToken, HttpServletRequest request);
}
