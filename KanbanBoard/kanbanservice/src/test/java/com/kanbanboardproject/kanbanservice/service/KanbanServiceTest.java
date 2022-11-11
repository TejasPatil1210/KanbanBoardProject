package com.kanbanboardproject.kanbanservice.service;


import com.kanbanboardproject.kanbanservice.amqpConfig.Producer;
import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.exception.*;
import com.kanbanboardproject.kanbanservice.repository.KanbanRepository;
import com.kanbanboardproject.kanbanservice.service.KanbanServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KanbanServiceTest {

    @Mock
    private KanbanRepository kanbanRepository;

    @InjectMocks
    private KanbanServiceImpl kanbanService;

    @Mock
    private Producer producer;
    private User user1, user2,user3,user4;

    private Project project1, project2,project3;
    private Column column1, column2,column3;
    private Task task1, task2, task3, task4,task5;
    private ProjectMember projectMember1, projectMember2;

    List<User> userList;
    List<Project> projectList=new ArrayList<>();
    List<Column> columnList=new ArrayList<>();
    List<Column> columnList1=new ArrayList<>();
    List<Task> taskList=new ArrayList<>();
    List<Task> taskList1=new ArrayList<>();
    List<ProjectMember> projectMemberList=new ArrayList<>();

    @BeforeEach
    public void setUp() {
        task1 = new Task("TASK123456", "Create FrontEnd", "create FrontEnd", "High", "Default",
                new Date(2022, 05, 12), "ABC");
        task2 = new Task("TASK456123", "Create BackEnd", "create BackEnd", "Low", "Default",
                new Date(2020, 04, 25), "ABC");
        task3 = new Task("TASK145675", "Create FrontEnd1", "create FrontEnd1", "High", "Urgent Work",
                new Date(2022, 07, 14), "ABC");
        task4 = new Task("TASK456321", "Create BackEnd1", "create BackEnd1", "Low", "Default",
                new Date(2020, 01, 27),"ABC");
        task5 = new Task("TASK424521", "Create Backend", "create Backend", "Low", "Default",
                new Date(2020, 01, 27), "ABC");

        taskList.add(task1);taskList.add(task2);
        taskList1.add(task3);taskList1.add(task4);
        column1 = new Column("COL123456", "TO do", taskList);
        column2 = new Column("COL156123", "In Progress", taskList1);
        column3 = new Column("COL125614", "Done", taskList);
        columnList.add(column1);columnList.add(column2);
        project1 = new Project("PROJ123456", "abc",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
        project2=new Project("PROJ456123","proj1",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
        project3=new Project("PROJ452312","proj2",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
        projectList.add(project1);


        projectMember1 = new ProjectMember("abc123@gmail.com", "ABC", 1);
        projectMember2 = new ProjectMember("xyz12345@gmail.com", "XYZ", 2);
        projectMemberList.add(projectMember1);
        user1 = new User("abc@gmail.com", "123456", "abc", projectList, projectMemberList);
        user2 = new User("xyz12@gmail.com", "123456", "XYZ", projectList, projectMemberList);
        user3 = new User("abc123456@gmail.com", "123456", "abc", projectList, projectMemberList);
        user4 = new User("abc1236@gmail.com", "123456", "abc", projectList, projectMemberList);
        userList = Arrays.asList(user1, user2);
    }

    @AfterEach
    public void teardown()
    {
        user1=null;
        user2=null;
    }

    @Test
    public void checkSaveUserDetails() throws UserAlreadyExistException, UserNotFoundException, ProjectMemberAlreadyExistException
    {
        when(kanbanRepository.findById(user4.getUserEmail())) .thenReturn(Optional.ofNullable(null));
        // when(kanbanRepository.findById(user4.getUserEmail())).thenReturn(Optional.of(user4));
        when(kanbanRepository.save(any())).thenReturn(user4);
        assertEquals(user4,kanbanService.saveUserDetails(user4));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(1)).findById(anyString());
    }

    @Test
    public void checkCreateProject()  throws UserNotFoundException, ProjectAlreadyExistException
    {

        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.createProject(user2.getUserEmail(),project2));
        assertEquals(user1,kanbanService.createProject(user1.getUserEmail(),project2));
        assertThrows(ProjectAlreadyExistException.class,()->kanbanService.createProject(user1.getUserEmail(),project1));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(5)).findById(anyString());
    }

    @Test
    public void checkUpdateProject()  throws UserNotFoundException, ProjectNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.updateProject(user2.getUserEmail(),project2));
        assertEquals(user1,kanbanService.updateProject(user1.getUserEmail(),project1));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.updateProject(user1.getUserEmail(),project2));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(5)).findById(anyString());
    }

    @Test
    public void checkDeleteProject()  throws UserNotFoundException, ProjectNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.deleteProject(user2.getUserEmail(),project2.getProjectName()));
        assertEquals(user1,kanbanService.deleteProject(user1.getUserEmail(),project1.getProjectName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.deleteProject(user1.getUserEmail(),project2.getProjectName()));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(5)).findById(anyString());
    }

    @Test
    public void checkCreateColumn()  throws UserNotFoundException, ProjectNotFoundException,ColumnAlreadyExistException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.createColumn(user2.getUserEmail(),project2.getProjectName(),column3));
        assertEquals(user1,kanbanService.createColumn(user1.getUserEmail(),project1.getProjectName(),column3));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.createColumn(user1.getUserEmail(),project2.getProjectName(),column3));
        assertThrows(ColumnAlreadyExistException.class,()->kanbanService.createColumn(user1.getUserEmail(),project1.getProjectName(),column1));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(7)).findById(anyString());
    }

    @Test
    public void checkUpdateColumn()  throws UserNotFoundException, ProjectNotFoundException,ColumnNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.updateColumn(user2.getUserEmail(),project2.getProjectName(),column2));
        assertEquals(user1,kanbanService.updateColumn(user1.getUserEmail(),project1.getProjectName(),column2));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.updateColumn(user1.getUserEmail(),project2.getProjectName(),column2));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.updateColumn(user1.getUserEmail(),project1.getProjectName(),column3));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(7)).findById(anyString());
    }

    @Test
    public void checkDeleteColumn()  throws UserNotFoundException, ProjectNotFoundException,ColumnNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.deleteColumn(user2.getUserEmail(),project2.getProjectName(),column2.getColumnName()));
        assertEquals(user1,kanbanService.deleteColumn(user1.getUserEmail(),project1.getProjectName(),column2.getColumnName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.deleteColumn(user1.getUserEmail(),project2.getProjectName(),column2.getColumnName()));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.deleteColumn(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName()));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(7)).findById(anyString());
    }

    @Test
    public void checkCreateTask()  throws UserNotFoundException, ProjectNotFoundException,ColumnNotFoundException,TaskAlreadyExistException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.createTask(user2.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task5));
        assertEquals(user1,kanbanService.createTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task5));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.createTask(user1.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task5));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.createTask(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName(),task5));
        assertThrows(TaskAlreadyExistException.class,()->kanbanService.createTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(9)).findById(anyString());
    }

    @Test
    public void checkUpdateTask()  throws UserNotFoundException, ProjectNotFoundException,ColumnNotFoundException,TaskNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.updateTask(user2.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task1));
        assertEquals(user1,kanbanService.updateTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.updateTask(user1.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task1));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.updateTask(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName(),task1));
        assertThrows(TaskNotFoundException.class,()->kanbanService.updateTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task5));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(9)).findById(anyString());
    }

    @Test
    public void checkDeleteTask()  throws UserNotFoundException, ProjectNotFoundException,ColumnNotFoundException,TaskNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.deleteTask(user2.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertEquals(user1,kanbanService.deleteTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.deleteTask(user1.getUserEmail(),project2.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.deleteTask(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName(),task1.getTaskId()));
        assertThrows(TaskNotFoundException.class,()->kanbanService.deleteTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task5.getTaskId()));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(9)).findById(anyString());
    }

    @Test
    public void checkGetAllProjects()  throws UserNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getAllProjects(user2.getUserEmail()));
        assertEquals(projectList,kanbanService.getAllProjects(user1.getUserEmail()));
        verify(kanbanRepository,times(3)).findById(anyString());
    }

    @Test
    public void checkGetAllColumns()  throws UserNotFoundException,ProjectNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getAllColumns(user2.getUserEmail(),project1.getProjectName()));
        assertEquals(columnList,kanbanService.getAllColumns(user1.getUserEmail(),project1.getProjectName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.getAllColumns(user1.getUserEmail(),project2.getProjectName()));
        verify(kanbanRepository,times(5)).findById(anyString());
    }

    @Test
    public void checkGetAllTasks()  throws UserNotFoundException,ProjectNotFoundException,ColumnNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getAllTasks(user2.getUserEmail(),project1.getProjectName(),column1.getColumnName()));
        assertEquals(taskList,kanbanService.getAllTasks(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.getAllTasks(user1.getUserEmail(),project2.getProjectName(),column1.getColumnName()));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.getAllTasks(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName()));
        verify(kanbanRepository,times(7)).findById(anyString());
    }

    @Test
    public void checkAddProjectMember() throws UserNotFoundException,ProjectMemberAlreadyExistException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        when(kanbanRepository.save(any())).thenReturn(user1);
        assertThrows(UserNotFoundException.class,()->kanbanService.addProjectMember(user2.getUserEmail(),projectMember2));
        assertEquals(user1,kanbanService.addProjectMember(user1.getUserEmail(),projectMember2));
        assertThrows(ProjectMemberAlreadyExistException.class,()->kanbanService.addProjectMember(user1.getUserEmail(),projectMember1));
        verify(kanbanRepository,times(1)).save(any());
        verify(kanbanRepository,times(5)).findById(anyString());
    }

    @Test
    public void checkGetAllProjectMembers()  throws UserNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getAllProjectMembers(user2.getUserEmail()));
        assertEquals(projectMemberList,kanbanService.getAllProjectMembers(user1.getUserEmail()));
        verify(kanbanRepository,times(3)).findById(anyString());
    }

    @Test
    public void checkGetProject()  throws UserNotFoundException,ProjectNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getProject(user2.getUserEmail(),project1.getProjectName()));
        assertEquals(project1,kanbanService.getProject(user1.getUserEmail(),project1.getProjectName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.getProject(user1.getUserEmail(),project3.getProjectName()));
    }

    @Test
    public void checkGetColumn()  throws UserNotFoundException,ProjectNotFoundException,ColumnNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getColumn(user2.getUserEmail(),project1.getProjectName(),column1.getColumnName()));
        assertEquals(column1,kanbanService.getColumn(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.getColumn(user1.getUserEmail(),project3.getProjectName(),column1.getColumnName()));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.getColumn(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName()));
    }

    @Test
    public void checkGetTask()  throws UserNotFoundException,ProjectNotFoundException,ColumnNotFoundException,TaskNotFoundException
    {
        when(kanbanRepository.findById(user1.getUserEmail())).thenReturn(Optional.of(user1));
        when(kanbanRepository.findById(user2.getUserEmail())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFoundException.class,()->kanbanService.getTask(user2.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertEquals(task1,kanbanService.getTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertThrows(ProjectNotFoundException.class,()->kanbanService.getTask(user1.getUserEmail(),project3.getProjectName(),column1.getColumnName(),task1.getTaskId()));
        assertThrows(ColumnNotFoundException.class,()->kanbanService.getTask(user1.getUserEmail(),project1.getProjectName(),column3.getColumnName(),task1.getTaskId()));
        assertThrows(TaskNotFoundException.class,()->kanbanService.getTask(user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task5.getTaskId()));
    }

}
