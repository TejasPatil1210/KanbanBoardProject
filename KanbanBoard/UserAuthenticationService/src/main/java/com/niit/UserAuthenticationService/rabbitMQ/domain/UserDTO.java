package com.niit.UserAuthenticationService.rabbitMQ.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userEmail;
    private String password;
    private String userName;
}
