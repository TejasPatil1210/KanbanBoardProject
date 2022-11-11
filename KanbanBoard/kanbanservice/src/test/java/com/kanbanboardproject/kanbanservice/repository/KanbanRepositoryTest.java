package com.kanbanboardproject.kanbanservice.repository;

import com.kanbanboardproject.kanbanservice.domain.*;
import com.kanbanboardproject.kanbanservice.repository.KanbanRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class KanbanRepositoryTest {
    @Autowired
    private KanbanRepository kanbanRepository;

    private User user1;

    private Project project1;
    private Column column1;
    private Task task1;
    private ProjectMember projectMember1;
    List<Project> projectList=new ArrayList<>();
    List<Column> columnList=new ArrayList<>();
    List<Task> taskList=new ArrayList<>();

    List<ProjectMember> projectMemberList=new ArrayList<>();
    @BeforeEach
    public void setUp()
    {
        task1 = new Task("TASK123456", "Create FrontEnd", "create FrontEnd", "High", "Default",
                new Date(2022, 05, 12),"ABC");
//        task2 = new Task("TASK456123", "Create BackEnd", "create BackEnd", "Low", "Default",
//                new Date(2020, 04, 25), "XYZ");
//        task3 = new Task("TASK145675", "Create FrontEnd1", "create FrontEnd1", "High", "Urgent Work",
//                new Date(2022, 07, 14), "ABC");
//        task4 = new Task("TASK456321", "Create BackEnd1", "create BackEnd1", "Low", "Default",
//                new Date(2020, 01, 27), "XYZ");
//        task5 = new Task("TASK424521", "Create Backend", "create Backend", "Low", "Default",
//                new Date(2020, 01, 27), "XYZ");

        taskList.add(task1);
//        taskList1.add(task3);taskList1.add(task4);
        column1 = new Column("COL123456", "TO do", taskList);
//        column2 = new Column("COL156123", "In Progress", taskList1);
//        column3 = new Column("COL125614", "Done", taskList);
        columnList.add(column1);
        project1 = new Project("PROJ123456", "abc",
                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
                columnList);
//        project2=new Project("PROJ456123","proj1",
//                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
//                columnList);
//        project3=new Project("PROJ452312","proj2",
//                "https://images.unsplash.com/photo-1534251623184-22cb7e61c526?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw2NDI5N3wwfDF8Y29sbGVjdGlvbnwxfDEwNjAwODc5fHx8fHwyfHwxNjY1MTM5Nzgw&ixlib=rb-1.2.1&q=80&w=15000",
//                columnList);
        projectList.add(project1);


        projectMember1 = new ProjectMember("abc123@gmail.com", "ABC", 1);
//        projectMember2 = new ProjectMember("xyz12345@gmail.com", "XYZ", 2);
        projectMemberList.add(projectMember1);
        user1 = new User("abcde@gmail.com", "abc", "abc", projectList, projectMemberList);
//        user2 = new User("xyz12@gmail.com", "123456", "XYZ", projectList, projectMemberList);
//        userList = Arrays.asList(user1, user2);
    }
    @AfterEach
    public void tearDown()
    {
        user1=null;
        task1=null;
        column1=null;
        project1=null;
        projectMember1=null;
    }

    @Test
    public void givenUserToSaveShouldReturnUser()
    {
        kanbanRepository.insert(user1);
        User user2=kanbanRepository.findById(user1.getUserEmail()).get();
        assertNotNull(user2);
        assertEquals(user1.getUserEmail(),user2.getUserEmail());
    }
}
