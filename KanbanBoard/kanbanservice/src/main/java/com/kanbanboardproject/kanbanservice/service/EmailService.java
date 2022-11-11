package com.kanbanboardproject.kanbanservice.service;

public interface EmailService {
    String sendCreateBoardLink(String userEmail,String projectName);
    String addProjectMemberLink(String userEmail,String memberEmail);
}
