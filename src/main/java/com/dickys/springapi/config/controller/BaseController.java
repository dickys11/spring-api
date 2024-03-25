package com.dickys.springapi.config.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController extends AbstractResponseHandler {

    public <T> ResponseEntity<ResultResponse<T>> generateResponse(T data, String msg, HttpStatus httpStatus) {
        return super.generateResponse(data, msg, httpStatus);
    }
}

