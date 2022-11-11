package com.niit.UserAuthenticationService.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.UserAuthenticationService.controller.UserAuthenticationController;
import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.repository.UserAuthenticationRepository;
import com.niit.UserAuthenticationService.service.EmailService;
import com.niit.UserAuthenticationService.service.EmailServiceImpl;
import com.niit.UserAuthenticationService.service.SecurityTokenImplementation;
import com.niit.UserAuthenticationService.service.UserAuthenticationServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @InjectMocks
    private UserAuthenticationController controller;
    private MockMvc mockMvc;
    @Mock
    private UserAuthenticationServiceImplementation service;
    //    @Mock
//    private UserAuthenticationRepository repository;
    @Mock
    private EmailServiceImpl emailService=new EmailServiceImpl();
    @Mock
    SecurityTokenImplementation securityTokenImplementation;

    private ObjectMapper objectMapper = new ObjectMapper();
    User user;

    @BeforeEach
    public void setup() {
        user = new User("Testing@email.com", "123","Testing");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }
    @AfterEach
    public void teardown()
    {
        user=new User();
    }

    @Test
    public void saveUserTest() throws Exception {
        when(service.saveUser(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void loginTest() throws Exception {
        when(service.findByUserEmailAndPassword(user.getUserEmail(), user.getPassword())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(service,times(1)).findByUserEmailAndPassword(anyString(),anyString());
        ;
    }

    @Test
    public void updateusertest() throws Exception
    {
        when(service.updateUser(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/update-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
