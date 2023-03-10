package com.adaskalov.ibproject.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("Invalid User ID.");
    }
}
