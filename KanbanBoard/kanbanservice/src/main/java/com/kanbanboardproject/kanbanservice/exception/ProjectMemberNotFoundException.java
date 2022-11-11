package com.kanbanboardproject.kanbanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Project Member Not Found")
public class ProjectMemberNotFoundException extends Exception{
}
