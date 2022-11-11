package com.niit.UserAuthenticationService.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User {
    @Id
    private String userEmail;
    private String password;
    private String userName;
}
