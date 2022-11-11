package com.niit.UserAuthenticationService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
