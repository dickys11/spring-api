package com.dickys.springapi.config;

import com.dickys.springapi.config.exception.expected.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse<Object>> exception(Exception exception) {
        return throwException(exception, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<ResultResponse<Object>> throwException(Throwable throwable, HttpStatus httpStatus, String msg) {
        msg = msg == null ? throwable.getLocalizedMessage() : msg;

        return new AbstractResponseHandler() {
            @Override
            public Object data() {
                return throwable;
            }
        }.done(msg, httpStatus);
    }
}
