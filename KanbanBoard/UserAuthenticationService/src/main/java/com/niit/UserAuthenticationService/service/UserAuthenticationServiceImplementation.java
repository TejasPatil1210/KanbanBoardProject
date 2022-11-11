package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationServiceImplementation implements UserAuthenticationService{
    private UserAuthenticationRepository userRepository;

    @Autowired
    public UserAuthenticationServiceImplementation(UserAuthenticationRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {

        return  userRepository.save(user);
    }

    @Override
    public User findByUserEmailAndPassword(String userEmail, String password) throws UserNotFoundException {
        User user = userRepository.findByUserEmailAndPassword(userEmail,password);
        if(user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public User searchEmail(String email) {
//        Optional<User> userFound = userRepository.findById(email);
//        if (userFound.isPresent()) {
//            return userFound.get();
//        }
//        return null;
//    }
//
    @Override
    public User updateUser(User user) {
        Optional<User> userFound = userRepository.findById(user.getUserEmail());
        if (userFound.isPresent()) {
            User user1=new User();
            user1.setUserEmail(user.getUserEmail());
            user1.setPassword(user.getPassword());
            user1.setUserName(user.getUserName());
           return userRepository.save(user1);
        }
        return null;
    }

    @Override
    public String getPassword(String email) {
        Optional<User> userObj = userRepository.findById(email);
        if(userObj.isPresent()){
            return userObj.get().getPassword();
        }
        return "this email id is not registered";
    }


    @Override
    public User getUser(String userEmail) throws UserNotFoundException {
        if(userRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=userRepository.findById(userEmail).get();
        return user;
    }
}
