package com.dickys.springapi.config.exception;

import com.dickys.springapi.config.controller.AbstractResponseHandler;
import com.dickys.springapi.config.controller.ResultResponse;
import com.dickys.springapi.config.exception.expected.AppException;
import com.dickys.springapi.config.exception.expected.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<Object>> exception(Exception exception) {
        return throwException(exception, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse<Object>> exception(AppException exception) {
        return throwException(exception, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResultResponse<Object>> exception(DataNotFoundException exception) {
        return throwException(exception, HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<Object>> exception(MethodArgumentNotValidException exception) {
        if (exception.getBindingResult().getFieldError() == null) {
            return throwException(exception, HttpStatus.BAD_REQUEST, null);
        }

        FieldError fieldError = exception.getBindingResult().getFieldError();
        if (fieldError == null) {
            return throwException(exception, HttpStatus.BAD_REQUEST, null);
        }

        String field = fieldError.getField();
        String msg = fieldError.getDefaultMessage();

        if (msg == null) {
            return throwException(exception, HttpStatus.BAD_REQUEST, null);
        }

        return throwException(exception, HttpStatus.BAD_REQUEST, "Field " + field + " " + msg);
    }

    private ResponseEntity<ResultResponse<Object>> throwException(Throwable throwable, HttpStatus httpStatus, String msg) {
        msg = msg == null ? throwable.getLocalizedMessage() : msg;

        return new AbstractResponseHandler() {
        }.generateResponse(throwable, msg, httpStatus);
    }
}
