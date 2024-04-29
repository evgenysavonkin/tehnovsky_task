package com.tehnovsky.task.util.exceptions;

public class UserAccountsDontExistException extends RuntimeException {
    public UserAccountsDontExistException(String message) {
        super(message);
    }
}
