package com.iobuilder.user.domain.exception;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super();
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}
