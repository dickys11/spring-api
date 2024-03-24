package com.dickys.springapi.config.exception.expected;

public class AppException extends RuntimeException {
    public AppException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AppException(String message) {
        super(message);
    }
}
