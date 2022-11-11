package com.kanbanboardproject.kanbanservice.service;

import com.kanbanboardproject.kanbanservice.amqpConfig.Producer;
import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.dto.UserDTO;
import com.kanbanboardproject.kanbanservice.exception.*;
import com.kanbanboardproject.kanbanservice.repository.KanbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KanbanServiceImpl implements KanbanService{

    private KanbanRepository kanbanRepository;
    private Producer producer;

    @Autowired
    public KanbanServiceImpl(KanbanRepository kanbanRepository,Producer producer)
    {
        this.kanbanRepository = kanbanRepository;
        this.producer=producer;
    }

    @Override
    public User saveUserDetails(User user) throws UserAlreadyExistException,UserNotFoundException, ProjectMemberAlreadyExistException {
        UserDTO userDTO=new UserDTO();
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setUserName(user.getUserName());
        if(kanbanRepository.findById(user.getUserEmail()).isPresent())
        {
            throw new UserAlreadyExistException();
        }
        else {
            kanbanRepository.save(user);
            producer.sendMessageToRabbitMqServer(userDTO);
            ProjectMember projectMember=new ProjectMember(user.getUserEmail(), user.getUserName(), 0);
            this.addProjectMember(user.getUserEmail(), projectMember);

        }
        return user;
//        return kanbanRepository.save(user);
    }

    @Override
    public User createProject(String userEmail, Project project) throws UserNotFoundException, ProjectAlreadyExistException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }

        User user=kanbanRepository.findById(userEmail).get();
        List<Project> projectList;
        if(user.getProjectLists()==null)
        {
            projectList=new ArrayList<>();
        }
        else
        {
            projectList=user.getProjectLists();
            for(Project project1:projectList)
            {
                if(project1.getProjectName().equals(project.getProjectName()))
                {
                    throw new ProjectAlreadyExistException();
                }
            }
        }
        projectList.add(project);
        user.setProjectLists(projectList);
        return kanbanRepository.save(user);
    }

    @Override
    public User updateProject(String userEmail, Project project) throws UserNotFoundException, ProjectNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        System.out.println(project.getProjectName());
        int projectCount=0;
        User user=kanbanRepository.findById(userEmail).get();
        System.out.println("Hiiiiii");
        List<Project> projectList=user.getProjectLists();
        for(Project project1:projectList)
            {
                if(project1.getProjectId().equals(project.getProjectId()))
                {
                    System.out.println(projectList.indexOf(project1));
                   projectList.set(projectList.indexOf(project1),project);
                   projectCount++;break;
                }
            }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
//        projectList.forEach(x-> System.out.println(x.getProjectName()));
        user.setProjectLists(projectList);
        //        List<Project> projectList;
//        if(user.getProjectLists()==null)
//        {
//            projectList=new ArrayList<>();
//        }
//        else
//        {
//            projectList=user.getProjectLists();
//            for(Project project1:projectList)
//            {
//                if(project1.getProjectName().equals(project.getProjectName()))
//                {
//                    throw new ProjectAlreadyExistException();
//                }
//            }
//        }
//        projectList.add(project);
//        user.setProjectLists(projectList);
        return kanbanRepository.save(user);
    }

    @Override
    public User deleteProject(String userEmail, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        System.out.println(projectName);
        User user=kanbanRepository.findById(userEmail).get();
        List<Project> projectList=user.getProjectLists();
        int count=0;
        for(Project project1:projectList)
        {
            if(project1.getProjectName().equals(projectName))
            {
                System.out.println(projectList.indexOf(project1));
                projectList.remove(project1);count++;break;
            }
        }
        if(count==0)
        {
            throw new ProjectNotFoundException();
        }
        user.setProjectLists(projectList);
        return kanbanRepository.save(user);
    }

    @Override
    public User createColumn(String userEmail, String projectName, Column column) throws UserNotFoundException, ProjectNotFoundException, ColumnAlreadyExistException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        List<Project> projectLists=user.getProjectLists();
        Project project=new Project();
        for(Project project1:projectLists)
        {
            if(project1.getProjectName().equals(projectName))
            {
                project=project1;break;
            }
            else
            {
                project=null;
            }
        }
        if(project==null)
        {
            throw new ProjectNotFoundException();
        }
        else {
            List<Column> columnList;
                if(project.getColumnList() ==null)
                {
                    columnList=new ArrayList<>();
                }
                else {
                    columnList=project.getColumnList();
                    for(Column column1:columnList)
                    {
                        if(column1.getColumnName().equals(column.getColumnName()))
                        {
                            throw new ColumnAlreadyExistException();
                        }

                    }
                }
                columnList.add(column);
                project.setColumnList(columnList);
            }
        return kanbanRepository.save(user);
        }

    @Override
    public User updateColumn(String userEmail, String projectName, Column column) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        int projectCount=0,columnCount=0;
        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;
                projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        for(Column column1:project1.getColumnList())
        {
            if(column1.getColumnId().equals(column.getColumnId()))
            {
               project1.getColumnList().set(project1.getColumnList().indexOf(column1),column);
               columnCount++;break;
            }
        }
        if(columnCount==0){
            throw new ColumnNotFoundException();
        }
        project1.setColumnList(project1.getColumnList());
//        user.setProjectLists(project1);

        return kanbanRepository.save(user);
    }

    @Override
    public User deleteColumn(String userEmail, String projectName, String columnName) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        int projectCount=0;int columnCount=0;
        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;projectCount++;break;
            }
        }
        if(projectCount==0)
        {
            throw new ProjectNotFoundException();
        }
        for(Column column1:project1.getColumnList())
        {
            if(column1.getColumnName().equals(columnName))
            {
                project1.getColumnList().remove(column1);
                columnCount++;break;
            }
        }
        if(columnCount==0)
        {
            throw new ColumnNotFoundException();
        }
        project1.setColumnList(project1.getColumnList());
        return kanbanRepository.save(user);
    }

    @Override
    public User createTask(String userEmail, String projectName, String columnName, Task task) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskAlreadyExistException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        List<Project> projectLists=user.getProjectLists();
        Project project=new Project();
        for(Project project1:projectLists)
        {
            if(project1.getProjectName().equals(projectName))
            {
                project=project1;break;
            }
            else
            {
                project=null;
            }
        }
        if(project==null)
        {
            throw new ProjectNotFoundException();
        }
        else {
            List<Column> columnList = project.getColumnList();
            Column column = new Column();
            for (Column column1 : columnList) {
                if (column1.getColumnName().equals(columnName)) {
                    column = column1;
                    break;
                } else {
                    column = null;
                }
            }
            if(column==null)
            {
                throw new ColumnNotFoundException();
            }
            else {
                List<Task> taskList;
                if (column.getTaskList() == null) {
                    taskList = new ArrayList<>();
                } else {
                    taskList = column.getTaskList();
                    for (Task task1 : taskList) {
                        if (task1.getTaskName().equals(task.getTaskName())) {
                            throw new TaskAlreadyExistException();
                        }
                    }
                }
                taskList.add(task);
                if(column.getColumnName().equalsIgnoreCase("Done") ||
                        column.getColumnName().equalsIgnoreCase("Closed - WON"))
                {
                    for(ProjectMember projectMember:user.getProjectMemberList())
                    {
                        if(projectMember.getMemberName().equals(task.getMemberName()))
                        {
                            projectMember.setNoOfTask(projectMember.getNoOfTask()-1);
                        }
                    }
                    //System.out.println("hiii1");
                    //this.decreaseNoOfTask(userEmail,task.getMemberName());
                }

                if(column.getColumnName().equalsIgnoreCase("To do") ||
                        column.getColumnName().equalsIgnoreCase("Leads"))
                {
                    for(ProjectMember projectMember:user.getProjectMemberList())
                    {
                        if(projectMember.getMemberName().equals(task.getMemberName()))
                        {
                            projectMember.setNoOfTask(projectMember.getNoOfTask()+1);
                        }
                    }
                    //System.out.println(user);
                    //System.out.println("hiii2");
//                    this.increaseNoOfTask(userEmail,task.getMemberName());
                }

                column.setTaskList(taskList);
            }
        }
        return kanbanRepository.save(user);
    }

    @Override
    public User updateTask(String userEmail, String projectName, String columnName, Task task) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        int projectCount=0;
        int columnCount=0;
        int taskCount=0;
        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;
                projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        Column column=new Column();
        for (Column column1:project1.getColumnList())
        {
            if(column1.getColumnName().equals(columnName))
            {
                column=column1;
                columnCount++;break;
            }
        }
        if(columnCount==0){
            throw new ColumnNotFoundException();
        }
        for(Task task1:column.getTaskList())
        {
            if(task1.getTaskId().equals(task.getTaskId()))
            {
                column.getTaskList().set(column.getTaskList().indexOf(task1),task);
                taskCount++;break;
            }
        }
        if(taskCount==0){
            throw new TaskNotFoundException();
        }
        column.setTaskList(column.getTaskList());
//        project1.setColumnList(project1.getColumnList());
        return kanbanRepository.save(user);
    }

    @Override
    public User deleteTask(String userEmail, String projectName, String columnName, String taskId) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        int projectCount=0;
        int columnCount=0;
        int taskCount=0;
        User user=kanbanRepository.findById(userEmail).get();
        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;
                projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        Column column=new Column();
        for (Column column1:project1.getColumnList())
        {
            if(column1.getColumnName().equals(columnName))
            {
                column=column1;columnCount++;break;
            }
        }
        if(columnCount==0){
            throw new ColumnNotFoundException();
        }
        for(Task task1:column.getTaskList())
        {
            if(task1.getTaskId().equals(taskId))
            {
                System.out.println(task1.getTaskName());
//                for(ProjectMember projectMember:user.getProjectMemberList())
//                {
//                    if(projectMember.getMemberName().equals(task1.getMemberName()))
//                    {
//                        projectMember.setNoOfTask(projectMember.getNoOfTask()-1);
//                    }
//                }
                column.getTaskList().remove(task1);taskCount++;break;
            }
        }
        if(taskCount==0){
            throw new TaskNotFoundException();
        }
        column.getTaskList().forEach(x->x.getTaskName());
        column.setTaskList(column.getTaskList());
        return kanbanRepository.save(user);
    }

    @Override
    public User deleteTaskFromAnyCol(String userEmail, String projectName, String columnName, String taskId) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskNotFoundException
    {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        int projectCount=0;
        int columnCount=0;
        int taskCount=0;
        User user=kanbanRepository.findById(userEmail).get();
        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;
                projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        Column column=new Column();
        for (Column column1:project1.getColumnList())
        {
            if(column1.getColumnName().equals(columnName))
            {
                column=column1;columnCount++;break;
            }
        }
        if(columnCount==0){
            throw new ColumnNotFoundException();
        }
        for(Task task1:column.getTaskList())
        {
            if(task1.getTaskId().equals(taskId))
            {
                System.out.println(task1.getTaskName());
                if(!column.getColumnName().equalsIgnoreCase("Done"))
                {
                    for (ProjectMember projectMember : user.getProjectMemberList()) {
                        if (projectMember.getMemberName().equals(task1.getMemberName())) {
                            projectMember.setNoOfTask(projectMember.getNoOfTask() - 1);
                        }
                    }
                }
                column.getTaskList().remove(task1);taskCount++;break;
            }
        }
        if(taskCount==0){
            throw new TaskNotFoundException();
        }
        column.getTaskList().forEach(x->x.getTaskName());
        column.setTaskList(column.getTaskList());
        return kanbanRepository.save(user);
    }

    @Override
    public List<Project> getAllProjects(String userEmail) throws UserNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        List<Project> projectList=user.getProjectLists();
        return projectList;
    }

    @Override
    public List<Column> getAllColumns(String userEmail, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        int projectCount=0;

        Project project1=new Project();
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        List<Column> columnList=project1.getColumnList();
        return columnList;
    }

    @Override
    public List<Task> getAllTasks(String userEmail, String projectName, String columnName) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        Project project1=new Project();
        int projectCount=0;
        int columnCount=0;
        for(Project project:user.getProjectLists())
        {
            if(project.getProjectName().equals(projectName))
            {
                project1=project;projectCount++;break;
            }
        }
        if(projectCount==0){
            throw new ProjectNotFoundException();
        }
        Column column=new Column();
        for(Column column1:project1.getColumnList())
        {
            if(column1.getColumnName().equals(columnName))
            {
                column=column1;columnCount++;break;
            }
        }
        if(columnCount==0){
            throw new ColumnNotFoundException();
        }
        List<Task> taskList=column.getTaskList();
        return taskList;
    }

    @Override
    public User addProjectMember(String userEmail, ProjectMember projectMember) throws UserNotFoundException, ProjectMemberAlreadyExistException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        List<ProjectMember> projectMemberList;
        if(user.getProjectMemberList()==null)
        {
            projectMemberList=new ArrayList<>();
        }
        else
        {
            projectMemberList=user.getProjectMemberList();
            for(ProjectMember projectMember1:projectMemberList)
            {
                if(projectMember1.getMemberEmail().equals(projectMember.getMemberEmail()))
                {
                    throw new ProjectMemberAlreadyExistException();
                }
            }
        }
        projectMemberList.add(projectMember);
        user.setProjectMemberList(projectMemberList);
        return kanbanRepository.save(user);
    }

    @Override
    public User deleteProjectMember(String userEmail,String memberEmail) throws UserNotFoundException,MemberNotFoundException {

        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = kanbanRepository.findById(userEmail).get();
        List<ProjectMember> projectMemberList =user.getProjectMemberList();
        int count=0;
        for(ProjectMember projectMember1:projectMemberList)
        {
            System.out.println(projectMember1.getMemberEmail());
            if(projectMember1.getMemberEmail().equals(memberEmail))
            {
                System.out.println(projectMemberList.indexOf(projectMember1));
                projectMemberList.remove(projectMember1);
                count++;
                break;
            }
        }
        if(count==0)
        {
            throw new MemberNotFoundException();
        }
        user.setProjectMemberList(projectMemberList);
        return kanbanRepository.save(user);
    }

    @Override
    public List<ProjectMember> getAllProjectMembers(String userEmail) throws UserNotFoundException {
        if(kanbanRepository.findById(userEmail).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user=kanbanRepository.findById(userEmail).get();
        List<ProjectMember> projectMemberList=user.getProjectMemberList();
        return projectMemberList;
    }

    @Override
    public List<User> getAllUsers() {
        return kanbanRepository.findAll();
    }

    @Override
    public User searchEmail(String email) {
        Optional<User> userFound = kanbanRepository.findById(email);
        if (userFound.isPresent()) {
            return userFound.get();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userFound = kanbanRepository.findById(user.getUserEmail());
        if (userFound.isPresent()) {
            User user1=new User();
            user1.setUserName(user.getUserName());
            user1.setUserEmail(user.getUserEmail());
            user1.setPassword(user.getPassword());
           user1.setProjectLists(user.getProjectLists());
            user1.setProjectMemberList(user.getProjectMemberList());
            UserDTO userDTO=new UserDTO();
            userDTO.setUserEmail(user.getUserEmail());
            userDTO.setPassword(user.getPassword());
            producer.sendMessageToRabbitMqServer(userDTO);
            return kanbanRepository.save(user1);

        }
        return null;
    }

//    @Override
//    public String getPassword(String email) {
//        Optional<User> userObj = kanbanRepository.findById(email);
//        if(userObj.isPresent()){
//            return userObj.get().getPassword();
//        }
//        return "this email id is not registered";
//    }

    @Override
    public String getProjectId()
    {
        String projectId="PROJ";

        for(int i=0;i<6;i++)
        {
            int i1=(int)(Math.random()*10);
            projectId=projectId+i1;
        }
        System.out.println(projectId);
        return projectId;
    }

    @Override
    public String getColumnId() {
        String colId="COL";

        for(int i=0;i<6;i++)
        {
            int i1=(int)(Math.random()*10);
            colId=colId+i1;
        }
        System.out.println(colId);
        return colId;

    }

    @Override
    public String getTaskId() {
        String taskId="TASK";

        for(int i=0;i<6;i++)
        {
            int i1=(int)(Math.random()*10);
            taskId=taskId+i1;
        }
        System.out.println(taskId);
        return taskId;
    }

    @Override
    public Project getProject(String userEmail, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        List<Project> projectList=this.getAllProjects(userEmail);
        Project project=new Project();
        int count=0;
        for(Project project1:projectList)
        {
            if(project1.getProjectName().equals(projectName))
            {
                project=project1;count++;
                break;
            }
        }
        if(count==0)
        {
            throw new ProjectNotFoundException();
        }
        return project;
    }

    @Override
    public Column getColumn(String userEmail, String projectName, String columnName) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException {
        List<Column> columnList=this.getAllColumns(userEmail,projectName);
        Column column=new Column();
        int count=0;
        for(Column column1:columnList)
        {
            if(column1.getColumnName().equals(columnName))
            {
                column=column1;count++;
                break;
            }
        }
        if(count==0)
        {
            throw new ColumnNotFoundException();
        }
        return column;
    }



    @Override
    public Task getTask(String userEmail, String projectName, String columnName, String taskId) throws UserNotFoundException, ProjectNotFoundException, ColumnNotFoundException, TaskNotFoundException {
        List<Task> taskList=this.getAllTasks(userEmail,projectName,columnName);
        Task task=new Task();
        int count=0;
        for (Task task1:taskList)
        {
            if(task1.getTaskId().equals(taskId))
            {
                task=task1;count++;
                break;
            }
        }
        if(count==0)
        {
            throw new TaskNotFoundException();
        }
        return task;
    }


    @Override
    public User increaseNoOfTask(String userEmail, String memberName) {
        System.out.println("increaseNoOfTask");
        User user=kanbanRepository.findById(userEmail).get();
        for(ProjectMember projectMember:user.getProjectMemberList())
        {
            if(projectMember.getMemberName().equals(memberName))
            {
                projectMember.setNoOfTask(projectMember.getNoOfTask()+1);
            }
        }
        System.out.println(user);
        return kanbanRepository.save(user);
    }

    @Override
    public User decreaseNoOfTask(String userEmail, String memberName) {
        System.out.println("decreaseNoOfTask");
        User user=kanbanRepository.findById(userEmail).get();
        for(ProjectMember projectMember:user.getProjectMemberList())
        {
            if(projectMember.getMemberName().equals(memberName))
            {
                projectMember.setNoOfTask(projectMember.getNoOfTask()-1);
            }
        }
        System.out.println(user);
        return kanbanRepository.save(user);
    }
}
