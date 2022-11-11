package com.kanbanboardproject.kanbanservice.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectMember {
    private String memberEmail;
    private String memberName;
    private int noOfTask;
}
