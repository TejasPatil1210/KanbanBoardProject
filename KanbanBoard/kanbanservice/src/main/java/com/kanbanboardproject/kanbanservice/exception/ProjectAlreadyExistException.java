package com.kanbanboardproject.kanbanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Project Already Exist")
public class ProjectAlreadyExistException extends Exception {
}
