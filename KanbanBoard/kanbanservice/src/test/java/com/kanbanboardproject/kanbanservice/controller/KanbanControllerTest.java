package com.kanbanboardproject.kanbanservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanbanboardproject.kanbanservice.controller.KanbanController;
import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.service.EmailService;
import com.kanbanboardproject.kanbanservice.service.KanbanService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class KanbanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private KanbanService kanbanService;

    @Mock
    private EmailService emailService;
    private User user1, user2, user3;

    private Project project1, project2;
    private Column column1, column2;
    private Task task1, task2, task3, task4;
    private ProjectMember projectMember1, projectMember2;

    List<User> userList;
    List<Project> projectList,projectList1;
    List<Column> columnList;
    List<Task> taskList;
    List<Task> taskList1;
    List<ProjectMember> projectMemberList,projectMemberList1;
    @InjectMocks
    private KanbanController kanbanController;

    @BeforeEach
    public void setUp() {
        task1 = new Task("TASK123456", "Create FrontEnd", "create FrontEnd", "High", "Default",
                new Date(2022, 05, 12), "ABC");
        task2 = new Task("TASK456123", "Create BackEnd", "create BackEnd", "Low", "Default",
                new Date(2020, 04, 25),"ABC");
        task3 = new Task("TASK145675", "Create FrontEnd1", "create FrontEnd1", "High", "Urgent Work",
                new Date(2022, 07, 14), "ABC");
        task4 = new Task("TASK456321", "Create BackEnd1", "create BackEnd1", "Low", "Default",
                new Date(2020, 01, 27), "ABC");
        taskList = Arrays.asList(task1, task2);
        taskList1 = Arrays.asList(task3, task4);
        column1 = new Column("COL123456", "TO do", taskList);
        column2 = new Column("COL123456", "In Progress", taskList1);

        columnList = Arrays.asList(column1, column2);

        project1 = new Project("PROJ123456", "abc",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
        project2=new Project("PROJ456123","proj1",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
        projectList = Arrays.asList(project1);

        projectMember1 = new ProjectMember("abc123@gmail.com", "ABC", 1);
        projectMember2 = new ProjectMember("xyz12345@gmail.com", "XYZ", 2);
        projectMemberList = Arrays.asList(projectMember1, projectMember2);
        user1 = new User("abc123@gmail.com", "123456", "ABC", projectList, projectMemberList);
        user2 = new User("xyz123@gmail.com", "123456", "XYZ", projectList, projectMemberList);
        user3 = new User("pqr123@gmail.com","123456","PQR",projectList1,projectMemberList1);
        userList = Arrays.asList(user1, user2,user3);
        //make mock http requests
        mockMvc = MockMvcBuilders.standaloneSetup(kanbanController).build();
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    public void checkRegisterUser() throws Exception {
        when(kanbanService.saveUserDetails(any())).thenReturn(user1);

        mockMvc.perform(post("/api/v2/kanban-service/user")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(user1)))//firstly, java object will be converted to json string then will
                //be passed with the mock http request
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());

        verify(kanbanService, times(1)).saveUserDetails(any());
    }

    @Test
    public void checkCreateProject() throws Exception {
        when(kanbanService.createProject(anyString(),any())).thenReturn(user3);
        when(emailService.sendCreateBoardLink(anyString(),any())).thenReturn("Mail Sent Successfully...!!!");
        mockMvc.perform(post("/api/v2/kanban-service/create-project/{userEmail}", user3.getUserEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(project1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).createProject(anyString(),any());
        //    verify(emailService, times(1)).sendCreateBoardLink(anyString(),any());
    }

    @Test
    public void checkUpdateProject() throws Exception {
        when(kanbanService.updateProject(anyString(),any())).thenReturn(user1);
        mockMvc.perform(put("/api/v2/kanban-service/update-project/{userEmail}", user1.getUserEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(project1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).updateProject(anyString(),any());

    }

    @Test
    public void checkDeleteProject() throws Exception {
        when(kanbanService.deleteProject(anyString(),anyString())).thenReturn(user1);
        mockMvc.perform(delete("/api/v2/kanban-service/delete-project/{userEmail}/{projectName}",
                        user1.getUserEmail(),project1.getProjectName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).deleteProject(anyString(),anyString());

    }

    @Test
    public void checkCreateColumn() throws Exception {
        when(kanbanService.createColumn(anyString(),anyString(),any())).thenReturn(user1);
        mockMvc.perform(post("/api/v2/kanban-service/create-column/{userEmail}/{projectName}", user1.getUserEmail(),project1.getProjectName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(column1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).createColumn(anyString(),anyString(),any());
    }

    @Test
    public void checkUpdateColumn() throws Exception {
        when(kanbanService.updateColumn(anyString(),anyString(),any())).thenReturn(user1);
        mockMvc.perform(put("/api/v2/kanban-service/update-column/{userEmail}/{projectName}",user1.getUserEmail(),project1.getProjectName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(column1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).updateColumn(anyString(),anyString(),any());

    }

    @Test
    public void checkDeleteColumn() throws Exception {
        when(kanbanService.deleteColumn(anyString(),anyString(),anyString())).thenReturn(user1);
        mockMvc.perform(delete("/api/v2/kanban-service/delete-column/{userEmail}/{projectName}/{columnName}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).deleteColumn(anyString(),anyString(),anyString());

    }

    @Test
    public void checkCreateTask() throws Exception {
        when(kanbanService.createTask(anyString(),anyString(),anyString(),any())).thenReturn(user1);
        mockMvc.perform(post("/api/v2/kanban-service/create-task/{userEmail}/{projectName}/{columnName}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(task1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).createTask(anyString(),anyString(),anyString(),any());
    }

    @Test
    public void checkUpdateTask() throws Exception {
        when(kanbanService.updateTask(anyString(),anyString(),anyString(),any())).thenReturn(user1);
        mockMvc.perform(put("/api/v2/kanban-service/update-task/{userEmail}/{projectName}/{columnName}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(task1)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).updateTask(anyString(),anyString(),anyString(),any());

    }

    @Test
    public void checkDeleteTask() throws Exception {
        when(kanbanService.deleteTask(anyString(),anyString(),anyString(),anyString())).thenReturn(user1);
        mockMvc.perform(delete("/api/v2/kanban-service/delete-task/{userEmail}/{projectName}/{columnName}/{taskId}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1.getTaskId()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).deleteTask(anyString(),anyString(),anyString(),anyString());

    }

    @Test
    public void checkGetAllProjects() throws Exception {
        when(kanbanService.getAllProjects(anyString())).thenReturn(projectList);
        mockMvc.perform(get("/api/v2/kanban-service/projects/{userEmail}",user1.getUserEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getAllProjects(anyString());

    }

    @Test
    public void checkGetAllColumns() throws Exception {
        when(kanbanService.getAllColumns(anyString(),anyString())).thenReturn(columnList);
        mockMvc.perform(get("/api/v2/kanban-service/columns/{userEmail}/{projectName}",
                        user1.getUserEmail(),project1.getProjectName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getAllColumns(anyString(),anyString());

    }

    @Test
    public void checkGetAllTasks() throws Exception {
        when(kanbanService.getAllTasks(anyString(),anyString(),anyString())).thenReturn(taskList);
        mockMvc.perform(get("/api/v2/kanban-service/tasks/{userEmail}/{projectName}/{columnName}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getAllTasks(anyString(),anyString(),anyString());
    }

    @Test
    public void checkGetProject() throws Exception {
        when(kanbanService.getProject(anyString(),anyString())).thenReturn(project1);
        mockMvc.perform(get("/api/v2/kanban-service/project/{userEmail}/{projectName}",
                        user1.getUserEmail(),project1.getProjectName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getProject(anyString(),anyString());
    }

    @Test
    public void checkGetColumn() throws Exception {
        when(kanbanService.getColumn(anyString(),anyString(),anyString())).thenReturn(column1);
        mockMvc.perform(get("/api/v2/kanban-service/column/{userEmail}/{projectName}/{columnName}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getColumn(anyString(),anyString(),anyString());
    }

    @Test
    public void checkGetTask() throws Exception {
        when(kanbanService.getTask(anyString(),anyString(),anyString(),anyString())).thenReturn(task1);
        mockMvc.perform(get("/api/v2/kanban-service/task/{userEmail}/{projectName}/{columnName}/{taskId}",
                        user1.getUserEmail(),project1.getProjectName(),column1.getColumnName(),task1.getTaskId()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getTask(anyString(),anyString(),anyString(),anyString());
    }

    @Test
    public void checkAddProjectMember() throws Exception {
        when(kanbanService.addProjectMember(anyString(),any())).thenReturn(user1);
        mockMvc.perform(post("/api/v2/kanban-service/add-project-member/{userEmail}",user1.getUserEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(projectMember2)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).addProjectMember(anyString(),any());

    }

    @Test
    public void checkGetAllProjectMembers() throws Exception {
        when(kanbanService.getAllProjectMembers(anyString())).thenReturn(projectMemberList);
        mockMvc.perform(get("/api/v2/kanban-service/project-members/{userEmail}",user1.getUserEmail()))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(kanbanService, times(1)).getAllProjectMembers(anyString());
    }
    private String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            System.out.println("Json Content that has been posted:\n" + jsonContent);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }
        return result;
    }
}

