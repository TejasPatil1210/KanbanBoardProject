package com.kanbanboardproject.kanbanservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Document
public class User {
    @Id
    private String userEmail;
    private String password;
    private String userName;
    private List<Project> projectLists;
    private List<ProjectMember> projectMemberList;
}
