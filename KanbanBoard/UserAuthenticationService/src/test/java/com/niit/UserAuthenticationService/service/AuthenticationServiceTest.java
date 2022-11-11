package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import com.niit.UserAuthenticationService.exception.UserNotFoundException;
import com.niit.UserAuthenticationService.repository.UserAuthenticationRepository;
import com.niit.UserAuthenticationService.service.UserAuthenticationServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.meta.When;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserAuthenticationRepository Repository;
    @InjectMocks
    private UserAuthenticationServiceImplementation serviceImplementation;
    private User user=new User();

    @BeforeEach
    public void setup()
    {
        user.setUserEmail("Testing@email.com");
        user.setPassword("123");
    }

    @Test
    public void SaveUserTesting() throws UserNotFoundException
    {
        when(Repository.save(user)).thenReturn(user);

        assertEquals(user,serviceImplementation.saveUser(user));
        verify(Repository,times(1)).save(any());
    }
    @Test
    public void findByUserEmailAndPasswordTest()throws UserNotFoundException
    {
        when(Repository.findByUserEmailAndPassword(user.getUserEmail(), user.getPassword())).thenReturn(user);
        assertEquals(user,serviceImplementation.findByUserEmailAndPassword(user.getUserEmail(), user.getPassword()));
    }

    @Test
    public void getPasswordTest()
    {
        when(Repository.findById(user.getUserEmail())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getPassword(),serviceImplementation.getPassword(user.getUserEmail()));
    }


}

