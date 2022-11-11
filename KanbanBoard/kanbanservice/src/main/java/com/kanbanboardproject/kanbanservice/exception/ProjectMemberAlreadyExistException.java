package com.kanbanboardproject.kanbanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Project Member Already Exist")
public class ProjectMemberAlreadyExistException extends Exception{
}
