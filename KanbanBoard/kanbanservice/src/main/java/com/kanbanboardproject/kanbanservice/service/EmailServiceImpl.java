package com.kanbanboardproject.kanbanservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")         //will assign value to sender
    private String sender;
    @Override
    public String sendCreateBoardLink(String userEmail,String projectName) {
        try{
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(userEmail);
            mailMessage.setText("Congratulations...!!! You have successfully Created Project Board: "+ projectName+" in Kanban board project management tool...!!!");
            mailMessage.setSubject("Successfully Created Project Board...!!!");
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...!!!";
        }
        catch (Exception e)
        {
            return "Error while Sending Mail...!!!";
        }
    }

    @Override
    public String addProjectMemberLink(String userEmail, String memberEmail) {
        try{
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(memberEmail);
            mailMessage.setText(userEmail+" added you in his Project team in Kanban board project management tool...!!!");
            mailMessage.setSubject("Added to the team...!!!");
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...!!!";
        }
        catch (Exception e)
        {
            return "Error while Sending Mail...!!!";
        }

    }
}
