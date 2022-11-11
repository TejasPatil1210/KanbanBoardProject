package com.kanbanboardproject.kanbanservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String userEmail;
    private String password;
    private String userName;
}
