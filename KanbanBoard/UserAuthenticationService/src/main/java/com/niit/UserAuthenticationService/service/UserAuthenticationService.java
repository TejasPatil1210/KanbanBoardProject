package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;

import java.util.List;

public interface UserAuthenticationService {
    public User saveUser(User user);
    public User findByUserEmailAndPassword(String email,String password) throws UserNotFoundException;
//    List<User> getAllUsers();
//    User searchEmail(String email);
   User updateUser(User user);
     String getPassword(String email);

    User getUser(String userEmail) throws UserNotFoundException;


}
