package com.niit.UserAuthenticationService.Config;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.niit.UserAuthenticationService.rabbitMQ.domain.UserDTO;
import com.niit.UserAuthenticationService.service.EmailService;
import com.niit.UserAuthenticationService.service.UserAuthenticationServiceImplementation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier
public class Consumer {
    @Autowired
    private UserAuthenticationServiceImplementation userService;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "kanban_queue")
    public void getDataFromRabbitMQ(UserDTO userDTO) throws UserAlreadyExistsException {
        User user = new User();
        user.setUserEmail(userDTO.getUserEmail());
        user.setPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        System.out.println("saving data in mysql" +user.getUserEmail()+" "+ user.getPassword()+" ");
        userService.saveUser(user);
        emailService.sendSimpleEmail(user);

    }

    @RabbitListener(queues = "update_queue")
    public  void updateDataFromRabbitMQ(UserDTO userDTO){
        User user = new User();
        user.setUserEmail(userDTO.getUserEmail());
        user.setPassword(userDTO.getPassword());

        System.out.println("saving data in mysql" +user.getUserEmail()+" "+ user.getPassword()+" ");
        userService.updateUser(user);
    }
}
