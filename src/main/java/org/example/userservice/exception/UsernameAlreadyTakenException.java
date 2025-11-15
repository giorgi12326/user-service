package org.example.userservice.exception;

public class UsernameAlreadyTakenException extends RuntimeException{
    public UsernameAlreadyTakenException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
