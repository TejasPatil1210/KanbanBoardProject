package com.kanbanboardproject.kanbanservice.repository;

import com.kanbanboardproject.kanbanservice.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project,String> {
    Project findByProjectName(String projectName);


}
