package com.niit.UserAuthenticationService.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.service.EmailService;
import com.niit.UserAuthenticationService.service.SecurityTokenGenerator;
import com.niit.UserAuthenticationService.service.UserAuthenticationService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/")
public class UserAuthenticationController {
    private ResponseEntity responseEntity;
    private UserAuthenticationService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserAuthenticationController( UserAuthenticationService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity saveUser(@RequestBody User user){
        User registeredUser = userService.saveUser(user);

        return responseEntity = new ResponseEntity("registered user", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @HystrixCommand(fallbackMethod = "fallbackLogin")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException {

        Map<String,String> map = null;
        try{
            User userObj = userService.findByUserEmailAndPassword(user.getUserEmail(), user.getPassword());
            System.out.println("Application paused");
           // Thread.sleep(70000);
            System.out.println("Application resumed");
            if(userObj.getUserEmail().equals(user.getUserEmail()))
            {
                map = securityTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity(map, HttpStatus.OK);
        }
        catch(UserNotFoundException e){
            throw  new UserNotFoundException();
        }
        catch(Exception e){
            responseEntity  = new ResponseEntity("Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<?> fallbackLogin(@RequestBody User user) throws InvalidCredentialsException {
        String msg = "login failed!! Try after some time!!!";
        return new ResponseEntity<>(msg,HttpStatus.GATEWAY_TIMEOUT);
    }
//
//    @GetMapping("/users")
//    public ResponseEntity getAllUsers(HttpServletRequest request){
//        List<User> list = userService.getAllUsers();
//        responseEntity = new ResponseEntity(list,HttpStatus.OK);
//        return responseEntity;
//    }
//
//    @GetMapping("/find-user/{email}")
//    public ResponseEntity<?> getUser(@PathVariable String email){
//        return new ResponseEntity<>(userService.searchEmail(email),HttpStatus.OK);
//    }
//
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }

    @GetMapping("/forgot-password/{userEmail}")
    public ResponseEntity<?> getPassword(@PathVariable String userEmail) throws UserNotFoundException{
       try{
           String msg=emailService.sendForgotEmailLink(userEmail);
           responseEntity=new ResponseEntity(msg,HttpStatus.CREATED);
       }catch (UserNotFoundException unf)
       {
           throw unf;
       }
       catch (Exception e)
       {
           responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return responseEntity;
    }

    @GetMapping("/get-user/{userEmail}")
    public ResponseEntity<?> getUser(@PathVariable String userEmail) throws UserNotFoundException
    {
        try{
            User user=userService.getUser(userEmail);
            responseEntity=new ResponseEntity(user,HttpStatus.CREATED);
        }catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
