package com.example.demo16.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateResourseException extends RuntimeException{

    public DuplicateResourseException(String message)
    {
        super(message);
    }
}
