package com.kanbanboardproject.kanbanservice.controller;

import com.kanbanboardproject.kanbanservice.domain.Project;
import com.kanbanboardproject.kanbanservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/save-project")
    public ResponseEntity<?> saveProject(@RequestBody Project project){
        return new ResponseEntity<> (projectService.saveProject(project), HttpStatus.OK);
    }

//    @GetMapping("/get-project/{projectName}")
//    public ResponseEntity<?> getProject(@PathVariable String projectName){
//        return new ResponseEntity<> (projectService.getProject(projectName),HttpStatus.OK);
//    }
}
