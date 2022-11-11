package com.kanbanboardproject.kanbanservice.domain;

import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Project {
    private String projectId;
    private String projectName;
    private String projectBgImage;
    private List<Column> columnList;

}
