package com.kanbanboardproject.kanbanservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {
    private String taskId;
    private String taskName;
    private String taskDescription;
    private String priority;
    private String cardType;
    private Date dueDate;
    private String memberName;


}
