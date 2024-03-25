package com.dickys.springapi.config.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseHandler {

    private static final boolean IS_DEVELOPMENT = true;

    public <T> ResponseEntity<ResultResponse<T>> generateResponse(T data, String msg, HttpStatus httpStatus) {
        if (data instanceof Exception processException) {
            return onError(msg, processException, httpStatus);
        } else {
            return onSuccess(msg, data, httpStatus);
        }
    }

    private <T> ResponseEntity<ResultResponse<T>> onError(String msg, Exception ex, HttpStatus httpStatus) {
        String debugInfo = IS_DEVELOPMENT ? ex.getLocalizedMessage() : null;

        MetaResponse metaResponse = new MetaResponse(httpStatus.value(), msg, debugInfo);

        ResultResponse<T> result = new ResultResponse<>("ERROR", null, metaResponse);
        return generateResponseEntity(result, httpStatus);
    }

    private <T> ResponseEntity<ResultResponse<T>> onSuccess(String msg, T any, HttpStatus httpStatus) {
        MetaResponse metaResponse = new MetaResponse(httpStatus.value(), msg, null);

        String status = HttpStatus.valueOf(metaResponse.getCode()).is2xxSuccessful() ? "OK" : "ERROR";
        ResultResponse<T> result = new ResultResponse<>(status, any, metaResponse);
        return generateResponseEntity(result, httpStatus);
    }

    private <T> ResponseEntity<ResultResponse<T>> generateResponseEntity(ResultResponse<T> result, HttpStatus code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(result, headers, code);
    }
}
