package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;

public interface EmailService {
    String sendSimpleEmail(User user) ;
    public String sendForgotEmailLink(String email) throws UserNotFoundException;
}
