package com.nisum.challenge.exception;

public class UserEmailAlreadyExistException extends RuntimeException{
    public UserEmailAlreadyExistException(String message) {
        super(message);
    }
}
