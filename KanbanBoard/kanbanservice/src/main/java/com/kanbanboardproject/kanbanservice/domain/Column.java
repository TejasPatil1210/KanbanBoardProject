package com.kanbanboardproject.kanbanservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Column {
    private String columnId;
    private String columnName;
    private List<Task> taskList;
}
