package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserAuthenticationService userAuthenticationService;
    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;
    @Value("${spring.mail.username}")         //will assign value to sender
    private String sender;

    @Override
    public String sendSimpleEmail(User user) {
        System.out.println(user.getUserEmail());
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getUserEmail());

            mailMessage.setText("Congratulations...!!! You have successfully registered to Kanban board project management tool...!!!");
            mailMessage.setSubject("Successfully registered to kanban board...!!!");
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...!!!";
        }catch (Exception e){
            return "Error while Sending Mail...!!!";
        }
    }

    @Override
    public String sendForgotEmailLink(String email) throws UserNotFoundException
    {
        System.out.println(email);
//        try{
            if (userAuthenticationRepository.findById(email).isEmpty())
            {
                throw new UserNotFoundException();
            }
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            String password=userAuthenticationService.getPassword(email);
            String otp="";

            for(int i=0;i<6;i++)
            {
                int i1=(int)(Math.random()*10);
                otp=otp+i1;
            }
            System.out.println(otp);

            mailMessage.setText("Your OTP is : " +otp);
            mailMessage.setSubject("forgot password reset link");
            javaMailSender.send(mailMessage);
            return otp;
//        }catch (Exception e){
//            return "Error while Sending Mail...!!!";
//        }
    }
}
