package com.kanbanboardproject.kanbanservice.controller;

import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.exception.*;
import com.kanbanboardproject.kanbanservice.service.EmailService;
import com.kanbanboardproject.kanbanservice.service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v2/kanban-service")
public class KanbanController {
    private ResponseEntity responseEntity;
    private KanbanService kanbanService;

    private EmailService emailService;

    @Autowired
    public KanbanController(KanbanService kanbanService,EmailService emailService)
    {
        this.kanbanService=kanbanService;
        this.emailService=emailService;
    }
    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistException
    {
        try {
            kanbanService.saveUserDetails(user);
            responseEntity = new ResponseEntity(user, HttpStatus.CREATED);
        }
        catch (UserAlreadyExistException uar)
        {
            throw uar;
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/create-project/{userEmail}")
    public ResponseEntity createProject(@PathVariable String userEmail, @RequestBody Project project) throws UserNotFoundException, ProjectAlreadyExistException
    {
        System.out.println(project.getProjectName());
        System.out.println(project.getColumnList().size());
       System.out.println(project.getProjectBgImage());
        try{
            project.setProjectId(kanbanService.getProjectId());
            for(Column c: project.getColumnList())
            {
                System.out.println("hiiii");
                c.setColumnId(kanbanService.getColumnId());
            }
            User user=kanbanService.createProject(userEmail,project);
            responseEntity=new ResponseEntity(project,HttpStatus.CREATED);
            String s=emailService.sendCreateBoardLink(userEmail,project.getProjectName());
            System.out.println(s);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectAlreadyExistException pae)
        {
            throw pae;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/update-project/{userEmail}")
    public ResponseEntity updateProject(@PathVariable String userEmail,@RequestBody Project project) throws UserNotFoundException, ProjectNotFoundException
    {
        try
        {
         User user=kanbanService.updateProject(userEmail,project);
         responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete-project/{userEmail}/{projectName}")
    public ResponseEntity deleteProject(@PathVariable String userEmail,@PathVariable String projectName) throws UserNotFoundException, ProjectNotFoundException
    {
        try
        {
            User user=kanbanService.deleteProject(userEmail,projectName);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/create-column/{userEmail}/{projectName}")
    public ResponseEntity createColumn(@PathVariable String userEmail,@PathVariable String projectName,@RequestBody Column column) throws  UserNotFoundException, ProjectNotFoundException, ColumnAlreadyExistException
    {
        try{
            column.setColumnId(kanbanService.getColumnId());
            User user = kanbanService.createColumn(userEmail, projectName, column);
            responseEntity = new ResponseEntity(column, HttpStatus.CREATED);

        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnAlreadyExistException cae)
        {
            throw cae;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/update-column/{userEmail}/{projectName}")
    public ResponseEntity updateColumn(@PathVariable String userEmail,@PathVariable String projectName,@RequestBody Column column) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException
    {
        try{
            User user=kanbanService.updateColumn(userEmail,projectName,column);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete-column/{userEmail}/{projectName}/{columnName}")
    public ResponseEntity deleteColumn(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException
    {
        try{
            User user=kanbanService.deleteColumn(userEmail,projectName,columnName);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/create-task/{userEmail}/{projectName}/{columnName}")
    public ResponseEntity createTask(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName,@RequestBody Task task) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskAlreadyExistException
    {
        try{
            if(task.getTaskId().length()==0) {
                task.setTaskId(kanbanService.getTaskId());
            }
            User user=kanbanService.createTask(userEmail,projectName,columnName,task);
            responseEntity=new ResponseEntity(task,HttpStatus.CREATED);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch (TaskAlreadyExistException tae)
        {
            throw tae;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/update-task/{userEmail}/{projectName}/{columnName}")
    public ResponseEntity updateTask(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName,@RequestBody Task task) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException
    {
        try{
            User user=kanbanService.updateTask(userEmail,projectName,columnName,task);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch (TaskNotFoundException tnf)
        {
            throw tnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @DeleteMapping("/delete-task/{userEmail}/{projectName}/{columnName}/{taskId}")
    public ResponseEntity deleteTask(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName,@PathVariable String taskId) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException
    {
        try{
            User user=kanbanService.deleteTask(userEmail,projectName,columnName,taskId);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch (TaskNotFoundException tnf)
        {
            throw tnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete-task-from-any-col/{userEmail}/{projectName}/{columnName}/{taskId}")
    public ResponseEntity deleteTaskFromAnyCol(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName,@PathVariable String taskId) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException
    {
        try{
            User user=kanbanService.deleteTaskFromAnyCol(userEmail,projectName,columnName,taskId);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch (TaskNotFoundException tnf)
        {
            throw tnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/projects/{userEmail}")
    public ResponseEntity getAllProjects(@PathVariable String userEmail) throws  UserNotFoundException
    {
        try{
           List<Project> projectList=kanbanService.getAllProjects(userEmail);
            responseEntity=new ResponseEntity(projectList,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/columns/{userEmail}/{projectName}")
    public ResponseEntity getAllColumns(@PathVariable String userEmail,@PathVariable String projectName) throws  UserNotFoundException, ProjectNotFoundException
    {
        try{
            List<Column> columnList=kanbanService.getAllColumns(userEmail,projectName);
            responseEntity=new ResponseEntity(columnList,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/tasks/{userEmail}/{projectName}/{columnName}")
    public ResponseEntity getAllTasks(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException
    {
        try{
            List<Task> taskList=kanbanService.getAllTasks(userEmail,projectName,columnName);
            responseEntity=new ResponseEntity(taskList,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/project/{userEmail}/{projectName}")
    public ResponseEntity getProject(@PathVariable String userEmail,@PathVariable String projectName) throws  UserNotFoundException, ProjectNotFoundException
    {
        try{
            Project project=kanbanService.getProject(userEmail,projectName);
            responseEntity=new ResponseEntity(project,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/column/{userEmail}/{projectName}/{columnName}")
    public ResponseEntity getColumn(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException
    {
        try{
            Column column=kanbanService.getColumn(userEmail,projectName,columnName);
            responseEntity=new ResponseEntity(column,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/task/{userEmail}/{projectName}/{columnName}/{taskId}")
    public ResponseEntity getTask(@PathVariable String userEmail,@PathVariable String projectName,@PathVariable String columnName,@PathVariable String taskId) throws  UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException,TaskNotFoundException
    {
        try{
            Task task=kanbanService.getTask(userEmail,projectName,columnName,taskId);
            responseEntity=new ResponseEntity(task,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectNotFoundException pnf)
        {
            throw pnf;
        }
        catch (ColumnNotFoundException cnf)
        {
            throw cnf;
        }
        catch (TaskNotFoundException tnf)
        {
            throw tnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/add-project-member/{userEmail}")
    public ResponseEntity addProjectMember(@PathVariable String userEmail, @RequestBody ProjectMember projectMember) throws  UserNotFoundException,ProjectMemberAlreadyExistException
    {
        try{
            User user=kanbanService.addProjectMember(userEmail,projectMember);
            responseEntity=new ResponseEntity(user,HttpStatus.CREATED);
            String s=emailService.addProjectMemberLink(userEmail,projectMember.getMemberEmail());
            System.out.println(s);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch (ProjectMemberAlreadyExistException pmae)
        {
            throw pmae;
        }
        catch(Exception e)
        {
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete-project-member/{userEmail}/{memberEmail}")
    public ResponseEntity deleteProjectMember(@PathVariable String userEmail,@PathVariable String memberEmail) throws UserNotFoundException, MemberNotFoundException
    {
        System.out.println("deleting member");
        try
        {
            User user=kanbanService.deleteProjectMember(userEmail,memberEmail);
            responseEntity=new ResponseEntity(user,HttpStatus.OK);

        }
        catch (UserNotFoundException unf)
        {
            System.out.println("user not found");
            throw unf;
        }
        catch (MemberNotFoundException mnf)
        {
            System.out.println("member not found");
            throw mnf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @GetMapping("/project-members/{userEmail}")
    public ResponseEntity  getAllProjectMembers(@PathVariable String userEmail) throws  UserNotFoundException
    {
        try{
            List<ProjectMember> projectMemberList=kanbanService.getAllProjectMembers(userEmail);
            responseEntity=new ResponseEntity(projectMemberList,HttpStatus.OK);
        }
        catch (UserNotFoundException unf)
        {
            throw unf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/get-users")
    public ResponseEntity getAllUsers(HttpServletRequest request){
        List<User> list = kanbanService.getAllUsers();
        responseEntity = new ResponseEntity(list,HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/find-user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        return new ResponseEntity<>(kanbanService.searchEmail(email),HttpStatus.OK);
    }

    @PutMapping("/update-user")
    public ResponseEntity updateUser(@RequestBody User user)  {
        try{
           User user2=kanbanService.updateUser(user);
           responseEntity=new ResponseEntity(user2,HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            responseEntity=new ResponseEntity("Error!!! Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}

