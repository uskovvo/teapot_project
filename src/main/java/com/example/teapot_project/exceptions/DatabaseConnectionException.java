package com.example.teapot_project.exceptions;

public class DatabaseConnectionException extends RuntimeException{
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
