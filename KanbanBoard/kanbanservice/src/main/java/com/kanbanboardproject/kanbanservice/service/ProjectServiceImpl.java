package com.kanbanboardproject.kanbanservice.service;

import com.kanbanboardproject.kanbanservice.domain.Project;
import com.kanbanboardproject.kanbanservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    ProjectRepository projectRepository;
    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

//    @Override
//    public Project getProject(String projectName) {
//        return projectRepository.findByProjectName(projectName);
//    }
}
