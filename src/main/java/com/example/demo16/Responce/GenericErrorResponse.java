package com.example.demo16.Responce;


import java.time.LocalDateTime;
import java.util.Map;

public class GenericErrorResponse {
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public GenericErrorResponse(Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

