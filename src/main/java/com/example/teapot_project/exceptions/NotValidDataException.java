package com.example.teapot_project.exceptions;

public class NotValidDataException extends RuntimeException{
    public NotValidDataException(String message) {
        super(message);
    }
}
