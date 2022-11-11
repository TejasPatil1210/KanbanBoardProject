package com.kanbanboardproject.kanbanservice.service;

import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.exception.*;

import java.util.List;

public interface KanbanService {
    User saveUserDetails(User user) throws UserAlreadyExistException,UserNotFoundException, ProjectMemberAlreadyExistException;
    User createProject(String userEmail, Project project) throws UserNotFoundException, ProjectAlreadyExistException;
    User updateProject(String userEmail, Project project) throws UserNotFoundException, ProjectNotFoundException;
    User deleteProject(String userEmail, String projectName) throws UserNotFoundException, ProjectNotFoundException;

    User createColumn(String userEmail, String projectName, Column column) throws UserNotFoundException, ProjectNotFoundException, ColumnAlreadyExistException;
    User updateColumn(String userEmail, String projectName, Column column) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException;
    User deleteColumn(String userEmail, String projectName, String columnName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException;

    User createTask(String userEmail, String projectName, String columnName, Task task) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskAlreadyExistException;
    User updateTask(String userEmail, String projectName, String columnName, Task task) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException;
    User deleteTask(String userEmail, String projectName, String columnName, String taskName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException;

    User deleteTaskFromAnyCol(String userEmail, String projectName, String columnName, String taskId) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskNotFoundException;
    List<Project> getAllProjects(String userEmail) throws  UserNotFoundException;
    List<Column> getAllColumns(String userEmail, String projectName) throws  UserNotFoundException,ProjectNotFoundException;
    List<Task> getAllTasks(String userEmail,String projectName,String columnName) throws  UserNotFoundException,ProjectNotFoundException,ColumnNotFoundException;
    User addProjectMember(String userEmail, ProjectMember projectMember) throws UserNotFoundException,ProjectMemberAlreadyExistException;
    User deleteProjectMember(String userEmail,String memberEmail) throws UserNotFoundException,MemberNotFoundException;
    List<ProjectMember> getAllProjectMembers(String userEmail) throws UserNotFoundException;
    List<User> getAllUsers();
    User searchEmail(String email);
    User updateUser(User user);
    String getProjectId();
    String getColumnId();
    String getTaskId();

    Project getProject(String userEmail,String projectName) throws  UserNotFoundException, ProjectNotFoundException;
    Column getColumn(String userEmail,String projectName,String columnName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException;
    Task getTask(String userEmail,String projectName,String columnName,String taskId) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException;

    User increaseNoOfTask(String userEmail,String memberName);
    User decreaseNoOfTask(String userEmail,String memberName);





}
