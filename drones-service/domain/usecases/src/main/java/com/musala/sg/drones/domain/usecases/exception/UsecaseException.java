package com.musala.sg.drones.domain.usecases.exception;

public class UsecaseException extends RuntimeException {
    public UsecaseException() {
    }

    public UsecaseException(String message) {
        super(message);
    }

    public UsecaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
